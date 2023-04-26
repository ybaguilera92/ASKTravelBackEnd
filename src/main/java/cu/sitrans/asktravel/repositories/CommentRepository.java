package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.Comment;
import cu.sitrans.asktravel.repositories.generic.BaseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseRepository <Comment> {}
