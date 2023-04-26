package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.models.types.QuestionListTypes;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.payload.request.QuestionListDTO;
import cu.sitrans.asktravel.repositories.CatalogRepository;
import cu.sitrans.asktravel.service.strategies.QuestionGetStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuestionsByStatusStrategy extends QuestionGetStrategy {

    @Autowired
    CatalogRepository catalogRepository;

    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {
        QuestionListDTO questionListDTO = (QuestionListDTO)baseDTO;
        pageable = PageRequest.of(baseDTO.getPageIndex(), baseDTO.getPageSize());
        Page<Question> page = questionRepository.findAllByStatus(pageable, catalogRepository.findById(questionListDTO.getStatus()).
                orElseThrow(() -> new EntityNotFoundException(Catalog.class, questionListDTO.getStatus())));
        return formatResultList(page);
    }
}
