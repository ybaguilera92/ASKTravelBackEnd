package cu.sitrans.asktravel.controllers;


import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.payload.request.QuestionDTO;
import cu.sitrans.asktravel.payload.request.QuestionListDTO;
import cu.sitrans.asktravel.payload.request.QuestionUpdateRequest;
import cu.sitrans.asktravel.payload.response.ResponseHandler;
import cu.sitrans.asktravel.service.AnswerService;
import cu.sitrans.asktravel.service.impl.QuestionServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable("id") String id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<?> countQuestion() {
        return ResponseEntity.ok(questionService.countQuestion());
    }

    @GetMapping
    public ResponseEntity<?> getQuestions(@Valid QuestionListDTO questionListDTO) {
        return ResponseEntity.ok(questionService.getQuestions(questionListDTO));
    }
    @GetMapping("/searchQuestion")
    public ResponseEntity<?> searchQuestions(@Valid QuestionListDTO questionListDTO) {
        return ResponseEntity.ok(questionService.searchQuestions(questionListDTO));
    }
    @PostMapping( consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createQuestion(@RequestPart("file") MultipartFile file,
                                            @RequestPart("data") @Valid QuestionDTO questionDTO) throws IOException {
        questionDTO.setMultipartFile(file);
        return ResponseHandler.generateResponse("Registro realizado satisfactoriamente", HttpStatus.OK, questionService.save(modelMapper.map(questionDTO, Question.class)));
    }

    @PutMapping
    public ResponseEntity<?> updateQuestion(@RequestPart("file") MultipartFile file,
                                            @RequestPart("data") @Valid QuestionDTO questionDTO) throws IOException{
        questionDTO.setMultipartFile(file);
        return ResponseHandler.generateResponse("Registro realizado satisfactoriamente", HttpStatus.OK, questionService.update(modelMapper.map(questionDTO, Question.class)));
    }

}
