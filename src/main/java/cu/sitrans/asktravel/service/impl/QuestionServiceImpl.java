package cu.sitrans.asktravel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.factory.QuestionListStrategyFactory;
import cu.sitrans.asktravel.models.*;
import cu.sitrans.asktravel.models.types.QuestionListTypes;
import cu.sitrans.asktravel.payload.request.QuestionListDTO;
import cu.sitrans.asktravel.repositories.*;
import cu.sitrans.asktravel.service.EmailService;
import cu.sitrans.asktravel.service.FileService;
import cu.sitrans.asktravel.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QuestionServiceImpl extends ReactServiceImpl implements QuestionService  {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${asktravel.app.url}")
    private String url;

    @Value("${asktravel.app.name}")
    private String nameapp;
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    FileService fileService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    QuestionListStrategyFactory factory;

    @Autowired
    EmailService emailService;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        super(questionRepository);
    }

    public Map<String, Object> findQuestionByPhrase(Integer pageNo, Integer pageSize, String phrase) {
       // System.out.println(phrase);
        TextCriteria criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).diacriticSensitive(false).matchingPhrase(phrase);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Query query = TextQuery.queryText(criteria).sortByScore().
                addCriteria(Criteria.where("answers").exists(true).ne(null));

        long count = mongoTemplate.count(query, Question.class);
       // System.out.println(count);
        List<Question> questions = mongoTemplate.find(query.with(pageable), Question.class);

        Page<Question> page = new PageImpl<>(questions, pageable, count);


        Map<String, Object> response = new HashMap<>();
        response.put("questions", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("viewMore", page.getTotalPages() > 1);

        return response;
    }

    @Override
    public Question getQuestionById(String id) {
        if (questionRepository.findById(id).isPresent()) {
            Question question = questionRepository.findById(id).get();
            try {
                question.setFileEncode(Base64.getEncoder().encodeToString(fileService.getFile(question.getFile().getId()).getFile().getData()));
                question.setVisit(question.getVisit() != null ? question.getVisit() + 1 : 1);
                questionRepository.save(question);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Unable to load " + question.getFile().getTitle(), ex);
            }

            return question;
        } else {
            throw new EntityNotFoundException(Question.class, "id", id.toString());
        }
    }

    @Override
    @Transactional
    public Question save(Question question) throws IOException {

        question.setAnswers(Collections.EMPTY_LIST);
        question.setReactions(Collections.EMPTY_LIST);
        question.setFollowers(Collections.EMPTY_LIST);

        if (question.getAnswer() != null) {
            question.getAnswer().setParent(question.getId());
            question.getAnswer().setReactions(Collections.EMPTY_LIST);
            question.setAnswers(Arrays.asList(answerRepository.save(question.getAnswer())));
        }

        if (question.getMultipartFile() != null) {
            File file = fileService.addFile(question.getMultipartFile());
            question.setFile(file);
            question.setMultipartFile(null);
            question.setFileEncode(Base64.getEncoder().encodeToString(fileService.getFile(question.getFile().getId()).getFile().getData()));
        }

        // tag agregado por el usuario
        question.getTags().forEach(tag -> {
            if (!catalogRepository.findById(tag.getId()).isPresent()) {
                catalogRepository.save(tag);
            }
        });

        if(question.getStatusCat() != null){
            question.setStatus(Arrays.asList(question.getStatusCat()));
        }

        question.setFollowers(Arrays.asList(userRepository.findById(question.getFollower()).orElseThrow(() -> new EntityNotFoundException(User.class, question.getFollower()))));

        return questionRepository.save(question);
    }

    @Override
    public Map<String, Object> getQuestions(QuestionListDTO questionListDTO) {
        QuestionListTypes questionListTypes = Arrays.stream(QuestionListTypes.values()).filter(type -> type.name().equals(questionListDTO.getType())).findFirst()
                .orElseThrow(()-> new TypeNotPresentException(questionListDTO.getType(), null));

       // return findQuestionByPhrase(questionListDTO.getPageIndex(),questionListDTO.getPageSize(), questionListDTO.getPhrase());
        return factory.getStrategy(questionListTypes).getList(questionListDTO);
    }
    @Override
    public Map<String, Object> searchQuestions(QuestionListDTO questionListDTO) {
        QuestionListTypes questionListTypes = Arrays.stream(QuestionListTypes.values()).filter(type -> type.name().equals(questionListDTO.getType())).findFirst()
                .orElseThrow(()-> new TypeNotPresentException(questionListDTO.getType(), null));
        //System.out.println("Hola");
        return findQuestionByPhrase(questionListDTO.getPageIndex(),questionListDTO.getPageSize(), questionListDTO.getPhrase());
        //return factory.getStrategy(questionListTypes).getList(questionListDTO);
    }
    @Override
    public Map<String, Object> countQuestion() {
        Map<String, Object> response = new HashMap<>();
        response.put("total", questionRepository.countAllByAndAnswersIsNotNull());

        return response;
    }

    @Override
    @Transactional
    public Question update(Question question) throws IOException {

        Question existingQuestion = questionRepository.findById(question.getId()).orElseThrow( ()-> new EntityNotFoundException(Question.class, question.getId()));
        //question.getUser().getEmail()
        if (question.getMultipartFile() != null) {
            File file = fileService.addFile(question.getMultipartFile());
            existingQuestion.setFile(file);
            existingQuestion.setMultipartFile(null);
            existingQuestion.setFileEncode(Base64.getEncoder().encodeToString(fileService.getFile(existingQuestion.getFile().getId()).getFile().getData()));
        }

        if (existingQuestion.getAnswers().isEmpty() || !existingQuestion.getAnswers().stream().anyMatch( answer -> answer.getId().equals(question.getAnswer().getId()) && answer.getUser().getId().equals(question.getFollower()))) {
            Answer answer = new Answer(question.getAnswer().getTitle(), question.getAnswer().getDescription());
            answer.setParent(question.getId());
            answer.setReactions(Collections.EMPTY_LIST);
            existingQuestion.getAnswers().add(answerRepository.save(answer));
        } else {
            for (Answer answer : existingQuestion.getAnswers()) {
                if (answer.getId().equals(question.getAnswer().getId()) && answer.getUser().getId().equals(question.getFollower())) {
                    answer.setTitle(question.getAnswer().getTitle());
                    answer.setExcerpt(question.getAnswer().getExcerpt());
                    answer.setDescription(question.getAnswer().getDescription());
                    answer.setParent(question.getId());
                    answerRepository.save(answer);
                    break;
                }
            }
        }

        if(existingQuestion.getFollowers() != null && existingQuestion.getFollowers().stream().noneMatch(follower -> follower.getId().equals(question.getFollower()))){
            existingQuestion.getFollowers().add(userRepository.findById(question.getFollower()).orElseThrow(() -> new EntityNotFoundException(User.class, question.getFollower())));
        } else {
            List<User> followers = new ArrayList<>();
            followers.add(userRepository.findById(question.getFollower()).orElseThrow(() -> new EntityNotFoundException(User.class, question.getFollower())));
            existingQuestion.setFollowers(followers);
        }

        // tag aprobado por el administrador  el cual fue agregado por el usuario
        question.getTags().forEach(tag -> {
            if (tag.getStatus().equals("I")) {
                tag.setStatus("A");
                catalogRepository.save(tag);
            }
        });
        try {

            sendAnswerEmail(existingQuestion.getUser(),existingQuestion);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        existingQuestion.setTags(question.getTags());
        existingQuestion.setTitle(question.getTitle());
        existingQuestion.setDescription(question.getDescription());
        existingQuestion.setStatus(question.getStatus());

        return questionRepository.save(existingQuestion);
    }
    private void sendAnswerEmail(User user,Question question)
            throws MessagingException, UnsupportedEncodingException, JsonProcessingException {
        System.out.println(user);
        Notification notification = notificationRepository.findByType("MAIL-NEW-ANSWER").orElse(null);
        String subject = "Nueva respuesta";
        Date fecha = new Date();
        DateFormat formatfecha= new SimpleDateFormat("dd/MM/yyyy");
        String aux= formatfecha.format(fecha);
        notification.setMessage(notification.getMessage().replace("[USUARIO]", user.getUsername()));
        notification.setMessage(notification.getMessage().replace("[FECHA]", aux));
        notification.setMessage(notification.getMessage().replace("[APLICACION]", nameapp));
        notification.setMessage(notification.getMessage().replace("[URL_ANSWER]", url+"/question/"+question.getId()));
        notification.setMessage(notification.getMessage().replace("[URL_APLICACION]", url));
        emailService.sendSMail(new Email(user.getEmail(), notification.getMessage(), subject, null));
    }
}
