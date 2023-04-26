package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.payload.request.QuestionListDTO;
import cu.sitrans.asktravel.repositories.CatalogRepository;
import cu.sitrans.asktravel.service.strategies.QuestionGetStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionsByPhraseStrategy extends QuestionGetStrategy {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {
        QuestionListDTO questionListDTO = (QuestionListDTO)baseDTO;
        pageable = PageRequest.of(baseDTO.getPageIndex(), baseDTO.getPageSize());

        TextCriteria criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).diacriticSensitive(false).matchingPhrase(questionListDTO.getPhrase());

        Query query = TextQuery.queryText(criteria).sortByScore().
                addCriteria(Criteria.where("answers").exists(true).ne(null));

        long count = mongoTemplate.count(query, Question.class);
        List<Question> questions = mongoTemplate.find(query.with(pageable), Question.class);

        Page<Question> page = new PageImpl<>(questions, pageable, count);

        return formatResultList(page);
    }
}
