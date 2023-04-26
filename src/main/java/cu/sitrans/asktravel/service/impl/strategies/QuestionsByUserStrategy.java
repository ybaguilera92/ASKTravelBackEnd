package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.repositories.UserRepository;
import cu.sitrans.asktravel.service.impl.security.services.UserDetailsImpl;
import cu.sitrans.asktravel.service.strategies.QuestionGetStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionsByUserStrategy extends QuestionGetStrategy {

    @Autowired
    UserRepository userRepository;

    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user =  userRepository.findById(userDetails.getId())
                .orElseThrow(()-> new EntityNotFoundException(User.class, userDetails.getId()));
        pageable = PageRequest.of(baseDTO.getPageIndex(), baseDTO.getPageSize());
        List<Question> questions = questionRepository.findQuestionByUserOrderByCreatedDateDesc(user)
                .stream().filter( question -> !question.getAnswers().isEmpty()).collect(Collectors.toList());

        Page page = new PageImpl<>(questions, pageable, questions.size());


        return formatResultList(page);
    }
}
