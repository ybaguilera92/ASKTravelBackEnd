package cu.sitrans.asktravel.service.impl;

import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.factory.AnswerListStrategyFactory;
import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.models.types.AnswerListTypes;
import cu.sitrans.asktravel.payload.request.AnswerDTO;
import cu.sitrans.asktravel.payload.request.AnswerListDTO;
import cu.sitrans.asktravel.repositories.AnswerRepository;
import cu.sitrans.asktravel.repositories.QuestionRepository;
import cu.sitrans.asktravel.service.AnswerService;
import cu.sitrans.asktravel.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl extends ReactServiceImpl implements AnswerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AnswerListStrategyFactory factory;

    private static final String[] PREPS_ARTS = {"se","cuáles","cuál","qué","cómo","cuándo","cuales","cual","que","como","cuando","a", "ante", "bajo", "cabe", "con", "contra", "de", "desde", "durante", "en" , "entre", "hacia", "hasta", "mediante", "para", "por", "según", "sin", "so", "sobre", "tras", "versus", "vía", "un", "una", "unos", "unas", "el", "los", "la", "las", "lo"};
    
    public AnswerServiceImpl(AnswerRepository answerRepository){
        super(answerRepository);
    }

    public List<Question> findQuestionByphrase(String question){
        TextCriteria  criteria = TextCriteria.forDefaultLanguage().matchingPhrase(question);

        Query query = TextQuery.queryText(criteria).sortByScore();
        List<Question> questions = mongoTemplate.find(query, Question.class);

        return questions;
    }

    @Override
    public Answer getAnswerById(String id) {
        if (answerRepository.findById(id).isPresent()) {
            return answerRepository.findById(id).get();
        } else {
            throw new EntityNotFoundException(Question.class, "id", id.toString());
        }
    }

    @Override
    @Transactional
    public Answer update(AnswerDTO answerDTO) {
        Answer answer = answerRepository.findById(answerDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(Answer.class, answerDTO.getId()));

        modelMapper.map(answerDTO, answer);
        if(answer.isBest()){
            Question question = questionRepository.findQuestionByTitle(answer.getTitle());
            question.getAnswers().forEach( ans -> {
                if(!ans.getId().equals(answer.getId())){
                    ans.setBest(false);
                    answerRepository.save(ans);
                }
            });
        }
        return answerRepository.save(answer);
    }

    @Override
    public List<Answer> getAnswersByQuestion(String question) {

       List<String> words = Arrays.asList(question.toLowerCase().split(" "));
        System.out.println(words.size());
       Set<String> stringSet = new HashSet<>(Arrays.asList(PREPS_ARTS));
       words = words.stream().filter(word -> !stringSet.contains(word)).collect(Collectors.toList());
        System.out.println(words.size());

        System.out.println( StringUtils.join(words, ", "));
        TextCriteria  criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).diacriticSensitive(false).matchingAny(String.valueOf(StringUtils.join(words, ", ")));

        Query query = TextQuery.queryText(criteria).sortByScore();

        List<Answer> answers = mongoTemplate.find(query, Answer.class);

        return answers;
    }

    @Override
    public Map<String, Object> countAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("total", answerRepository.count());
        response.put("best", answerRepository.countAllByBestIsTrue());

        return response;
    }

    @Override
    public Map<String, Object> getAnswers(AnswerListDTO answerListDTO) {
        AnswerListTypes answerListTypes = Arrays.stream(AnswerListTypes.values()).filter(type -> type.name().equals(answerListDTO.getType())).findFirst()
                .orElseThrow(()-> new TypeNotPresentException(answerListDTO.getType(), null));

        return factory.getStrategy(answerListTypes).getList(answerListDTO);
    }

}
