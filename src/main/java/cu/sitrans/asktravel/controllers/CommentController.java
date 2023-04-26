package cu.sitrans.asktravel.controllers;


import cu.sitrans.asktravel.models.Comment;
import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.models.Reaction;
import cu.sitrans.asktravel.payload.request.CommentDTO;
import cu.sitrans.asktravel.payload.request.QuestionDTO;
import cu.sitrans.asktravel.payload.response.ResponseHandler;
import cu.sitrans.asktravel.service.AnswerService;
import cu.sitrans.asktravel.service.CommentService;
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
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    /*@GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable("id") String id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }*/

    /*@GetMapping("/{question}")
    public ResponseEntity<?> makeQuestion(@RequestParam ("question") String question) {
        return ResponseEntity.ok(questionService.findQuestionByphrase(question));
    }*/

    /*@GetMapping()
    public ResponseEntity<?> getQuestions(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                          @RequestParam(required = false, defaultValue = "") String status,
                                          @RequestParam(required = false, defaultValue = "") String phrase,
                                          @RequestParam(required = false, defaultValue = "") String tag) {
        if(!phrase.equals("")){
            return ResponseEntity.ok(questionService.findQuestionByPhrase(pageNo, pageSize, phrase));
        } else {
            return ResponseEntity.ok(questionService.getQuestions(pageNo, pageSize, status, tag));
        }
    }*/

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CommentDTO commentDTO) {
        switch (commentDTO.getType()) {
            case "answer":
                return ResponseHandler.generateResponse("Registro realizado satisfactoriamente",
                        HttpStatus.OK, commentService.saveCommentAnswer (commentDTO.getObjectId(), modelMapper.map(commentDTO, Comment.class)));
            default:
                return ResponseHandler.generateResponse("Registro realizado satisfactoriamente",
                        HttpStatus.OK, commentService.saveComment(commentDTO.getObjectId(),modelMapper.map(commentDTO, Comment.class)));
        }

    }

   /* @PutMapping
    public ResponseEntity<?> updateQuestion(@RequestPart("file") MultipartFile file,
                                            @RequestPart("data") @Valid QuestionDTO questionDTO) throws IOException{
        questionDTO.setMultipartFile(file);
        return ResponseHandler.generateResponse("Registro realizado satisfactoriamente", HttpStatus.OK, questionService.update(modelMapper.map(questionDTO, Question.class)));
    }*/

}
