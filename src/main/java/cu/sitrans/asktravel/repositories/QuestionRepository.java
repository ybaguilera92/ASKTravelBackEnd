package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.repositories.generic.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends BaseRepository<Question> {
    List<Question> findQuestionByAnswerIsNull() ;
    Question findQuestionByTitle(String title);
    List<Question> findAllByAnswersIsNotNullOrderByCreatedDateDesc();
    Page<Question> findQuestionByAnswersIsNotNullOrderByCreatedDateDesc(Pageable pageable);
    Page<Question> findAllByVisitGreaterThanOrderByVisitDesc(Pageable pageable, int visit);
    Page<Question> findAllByStatusIsNull(Pageable pageable);
    Page<Question> findAllByStatus(Pageable pageable, Catalog status);
    Page<Question> findAllByTags(Pageable pageable, Catalog tag);
    Page<Question> findAllByTagsAndStatus(Pageable pageable,Catalog status, Catalog tag);
    Optional<Question> findByAnswersIn(List<Answer> answers);
    Integer countAllByAndAnswersIsNotNull();
    List<Question> findQuestionByUserAndAnswersIsNotNull(User user);
    List<Question> findQuestionByUserOrderByCreatedDateDesc(User user);
}
