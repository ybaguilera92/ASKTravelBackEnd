package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.RefreshToken;
import cu.sitrans.asktravel.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);

}
