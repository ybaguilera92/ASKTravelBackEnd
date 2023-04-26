package cu.sitrans.asktravel.controllers;


import cu.sitrans.asktravel.payload.request.AnswerListDTO;
import cu.sitrans.asktravel.payload.request.QuestionListDTO;
import cu.sitrans.asktravel.service.AnswerService;
import cu.sitrans.asktravel.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;


    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions(@Valid QuestionListDTO questionListDTO) {
        return ResponseEntity.ok(questionService.getQuestions(questionListDTO));
    }

    @GetMapping("/answers")
    public ResponseEntity<?> getQuestion(@Valid AnswerListDTO answerListDTO) {
        return ResponseEntity.ok(answerService.getAnswers(answerListDTO));
    }

}
