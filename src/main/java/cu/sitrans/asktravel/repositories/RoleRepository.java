package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.ERole;
import cu.sitrans.asktravel.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
