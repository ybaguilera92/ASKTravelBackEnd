package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.service.strategies.AnswerGetStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AllAnswersStrategy extends AnswerGetStrategy {

    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {
        pageable = PageRequest.of(baseDTO.getPageIndex(), baseDTO.getPageSize());
        Page<Answer> page = answerRepository.findAllByUserIsNotNullOrderByCreatedDateAsc(pageable);
        return this.sendResponse(page);
    }
}
