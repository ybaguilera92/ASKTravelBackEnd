package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {}
