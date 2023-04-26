package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.service.strategies.AnswerGetStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MostVotedAnswersStrategy extends AnswerGetStrategy {

    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {

        pageable = PageRequest.of(baseDTO.getPageIndex(), baseDTO.getPageSize());
        List<Answer> answers = answerRepository.findAllByUserIsNotNullOrderByCreatedDateAsc()
                .stream().filter(answer -> answer.getReactions() != null && answer.getReactions().size() >= 2).collect(Collectors.toList());
        Page page = new PageImpl(answers, pageable, answers.size());

        return this.sendResponse(page);
    }
}
