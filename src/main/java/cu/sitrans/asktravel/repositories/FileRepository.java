package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.models.File;
import cu.sitrans.asktravel.models.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends MongoRepository<File, String> {}
