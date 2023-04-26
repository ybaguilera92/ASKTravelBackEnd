package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.payload.request.AnswerDTO;
import cu.sitrans.asktravel.payload.request.AnswerListDTO;
import cu.sitrans.asktravel.payload.request.BaseDTO;

import java.util.List;
import java.util.Map;

public interface AnswerService {
    Answer getAnswerById(String id);
    Answer update(AnswerDTO answerDTO);
    List<Answer> getAnswersByQuestion(String question);
    Map<String, Object> countAll();
    Map<String, Object> getAnswers(AnswerListDTO answerListDTO);
}
