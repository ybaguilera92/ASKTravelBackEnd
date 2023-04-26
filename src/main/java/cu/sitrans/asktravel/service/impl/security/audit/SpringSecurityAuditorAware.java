package cu.sitrans.asktravel.service.impl.security.audit;

import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.repositories.UserRepository;
import cu.sitrans.asktravel.service.impl.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser") || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userRepository.findById(userDetails.getId());
    }
}
