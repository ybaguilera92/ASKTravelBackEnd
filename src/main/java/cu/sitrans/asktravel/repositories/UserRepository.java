package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional <User> findByVerificationCode(String verificationCode);
    Integer countAllByEnabledIsTrue();
}
