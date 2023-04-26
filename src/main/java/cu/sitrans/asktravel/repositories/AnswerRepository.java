package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.repositories.generic.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends BaseRepository<Answer> {
    Integer countAllByBestIsTrue();
    Integer countAllByBestIsTrueAndUser(User user);
    Page<Answer> findAllByUserIsNotNullOrderByCreatedDateAsc(Pageable pageable);
    List<Answer> findAllByUserIsNotNullOrderByCreatedDateAsc();
    Page<Answer> findAllByUserOrderByCreatedDateAsc(Pageable pageable, User user);
    List<Answer> findAllByUserOrderByCreatedDateAsc(User user);
    Page<Answer> findAllByUserAndBestIsTrueOrderByCreatedDateAsc(Pageable pageable,User user);
}
