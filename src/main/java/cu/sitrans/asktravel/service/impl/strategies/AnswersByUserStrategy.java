package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.repositories.UserRepository;
import cu.sitrans.asktravel.service.impl.security.services.UserDetailsImpl;
import cu.sitrans.asktravel.service.strategies.AnswerGetStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AnswersByUserStrategy extends AnswerGetStrategy {

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
        Page<Answer> page = answerRepository.findAllByUserOrderByCreatedDateAsc(pageable, user);
        return this.sendResponse(page);
    }
}
