package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusRepository extends MongoRepository<Status, String> {}
