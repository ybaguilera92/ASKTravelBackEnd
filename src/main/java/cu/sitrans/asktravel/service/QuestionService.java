package cu.sitrans.asktravel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.payload.request.QuestionListDTO;
import cu.sitrans.asktravel.payload.request.QuestionUpdateRequest;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface QuestionService {
    Question getQuestionById(String id) throws IOException;
    Question save(Question question) throws IOException;
    Question update (Question question) throws IOException;
    Map<String, Object> findQuestionByPhrase(Integer pageNo, Integer pageSize, String phrase);
    Map<String, Object> getQuestions(QuestionListDTO questionListDTO);
    Map<String, Object> searchQuestions(QuestionListDTO questionListDTO);
    Map<String, Object> countQuestion();

}
