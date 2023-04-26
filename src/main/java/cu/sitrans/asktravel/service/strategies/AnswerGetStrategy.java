package cu.sitrans.asktravel.service.strategies;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public abstract class AnswerGetStrategy implements BaseGetStrategy<Answer> {

    @Autowired
    protected AnswerRepository answerRepository;
    protected Pageable pageable;

    public  Map<String, Object> sendResponse(Page page){

        Map<String, Object> response = new HashMap<>();
        response.put("answers", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return response;
    }

}
