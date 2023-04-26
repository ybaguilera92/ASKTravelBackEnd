package cu.sitrans.asktravel.controllers;


import cu.sitrans.asktravel.payload.request.AnswerDTO;
import cu.sitrans.asktravel.payload.request.AnswerListDTO;
import cu.sitrans.asktravel.payload.response.ResponseHandler;
import cu.sitrans.asktravel.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping("/count")
    public ResponseEntity<?> countAnswers() {
        return ResponseEntity.ok(answerService.countAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable("id") String id) {
        return ResponseEntity.ok(answerService.getAnswerById(id));
    }

    @GetMapping
    public ResponseEntity<?> getQuestion(@Valid AnswerListDTO answerListDTO) {
        return ResponseEntity.ok(answerService.getAnswers(answerListDTO));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody AnswerDTO answerDTO){
        return ResponseHandler.generateResponse("Actualizacion realizada con éxito", HttpStatus.OK, answerService.update(answerDTO));
    }

}
