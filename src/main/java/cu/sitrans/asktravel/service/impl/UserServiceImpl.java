package cu.sitrans.asktravel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.factory.QuestionListStrategyFactory;
import cu.sitrans.asktravel.models.*;
import cu.sitrans.asktravel.payload.request.UserChangePasswordDTO;
import cu.sitrans.asktravel.payload.request.UserDTO;
import cu.sitrans.asktravel.payload.request.UserUpdateImageDTO;
import cu.sitrans.asktravel.repositories.*;
import cu.sitrans.asktravel.service.EmailService;
import cu.sitrans.asktravel.service.FileService;
import cu.sitrans.asktravel.service.UserService;
import org.assertj.core.util.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Value("${asktravel.app.url}")
    private String url;

    @Value("${asktravel.app.name}")
    private String nameapp;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileService fileService;

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    EmailService emailService;

    /*@Override
    public Tag save(String tagTitle, String tagColor) {
        Tag tag = new Tag();
        tag.setTitle(tagTitle);
        tag.setColor(tagColor);
        // return tagRepository.save(tag);
    }*/

    @Override
    public Map<String, Object> getUsers(Pageable pageable) {

        Page<User> page = userRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("users", Lists.newArrayList(page));
        response.put("page", page);

        return response;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(UserDTO userDTO){

        User user = new User();
        this.modelMapper.map(userDTO,user);
        user.setPassword(encoder.encode(user.getPassword()));
      //  user.setUsername(user.getEmail().substring(0, user.getEmail().indexOf('@')));
        //user.setProfile(profileRepository.save(new Profile(user.getName(), user.getName())));
        user.setRoles(this.getRoles(userDTO));

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(UserDTO userDTO) {

        User user = userRepository.findById(userDTO.getId())
                .orElseThrow( () -> new EntityNotFoundException(User.class, userDTO.getId()));
        modelMapper.map(userDTO, user);
        user.setRoles(this.getRoles(userDTO));

        return userRepository.save(user);
    }

    @Override
    public User updatePassword(UserChangePasswordDTO userDTO) {
//        System.out.println("Hola");
//        System.out.println(userDTO);
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow( () -> new EntityNotFoundException(User.class, userDTO.getId()));
        modelMapper.map(userDTO, user);
        user.setPassword(encoder.encode(userDTO.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User updateImage(UserUpdateImageDTO userDTO) throws IOException {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow( () -> new EntityNotFoundException(User.class, userDTO.getId()));
        modelMapper.map(userDTO, user);
        if (userDTO.getMultipartFilea() != null) {
            System.out.println(userDTO.getMultipartFilea());
            File file = fileService.addFile(user.getMultipartFilea());
            user.setAvatar(file);
            user.setMultipartFilea(null);
            user.setFileEncode(Base64.getEncoder().encodeToString(fileService.getFile(user.getAvatar().getId()).getFile().getData()));
        }
        return userRepository.save(user);
    }

    @Override
    public Map<String, Object> countALl() {
        Map<String, Object> response = new HashMap<>();
        response.put("total", userRepository.countAllByEnabledIsTrue());

        return response;
    }

    @Override
    public Map<String, Object> getProfile(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));

//        if(user.getProfile() == null){
//            user.setProfile(profileRepository.save(new Profile(user.getName(), user.getName())));
//            userRepository.save(user);
//        }
        List<Question> questions = questionRepository.findQuestionByUserOrderByCreatedDateDesc(user)
                .stream().filter( question -> question.getAnswers().isEmpty()).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();

       // response.put("profile", user.getProfile());
        response.put("questions", questionRepository.findQuestionByUserAndAnswersIsNotNull(user).stream().count());
        response.put("answers", answerRepository.findAllByUserOrderByCreatedDateAsc(user).stream().count());
        response.put("bestAnswers", answerRepository.countAllByBestIsTrueAndUser(user));
        response.put("pendienting", questions.stream().count());

        return response;

    }

    private Set<Role> getRoles(UserDTO userDTO){
        Set<Role> roles = new HashSet<>();
        Arrays.stream(userDTO.getRoles()).forEach( role -> {
            roles.add(roleRepository.findByName(Arrays.stream(ERole.values()).filter(erol -> erol.name().equals(role)).findFirst()
                            .orElseThrow(() -> new TypeNotPresentException(role, null)))
                    .orElseThrow(() -> new EntityNotFoundException(Role.class, role)));
        });

        return roles;
    }

}
