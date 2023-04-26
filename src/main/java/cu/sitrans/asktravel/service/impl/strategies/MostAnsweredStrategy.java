package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.service.strategies.QuestionGetStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MostAnsweredStrategy extends QuestionGetStrategy {


    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {
        pageable = PageRequest.of(baseDTO.getPageIndex(), baseDTO.getPageSize());
        List<Question > questions = questionRepository.findAllByAnswersIsNotNullOrderByCreatedDateDesc().stream().filter(question -> question.getAnswers().size() >= 2).collect(Collectors.toList());
        Page page = new PageImpl<>(questions, pageable, questions.size());
        return formatResultList(page);
    }
}
