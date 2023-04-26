package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.service.strategies.QuestionGetStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AllQuestionsStrategy extends QuestionGetStrategy {


    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {
        pageable = PageRequest.of(baseDTO.getPageIndex(), baseDTO.getPageSize());
        Page<Question> page = questionRepository.findQuestionByAnswersIsNotNullOrderByCreatedDateDesc(pageable);
        return formatResultList(page);
    }
}
