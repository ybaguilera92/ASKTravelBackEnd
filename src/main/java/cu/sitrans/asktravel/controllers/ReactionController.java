package cu.sitrans.asktravel.controllers;

import cu.sitrans.asktravel.models.*;
import cu.sitrans.asktravel.models.types.PostTypes;
import cu.sitrans.asktravel.payload.request.ReactionDTO;
import cu.sitrans.asktravel.payload.response.ResponseHandler;
import cu.sitrans.asktravel.repositories.QuestionRepository;
import cu.sitrans.asktravel.service.ReactionService;
import cu.sitrans.asktravel.service.impl.AnswerServiceImpl;
import cu.sitrans.asktravel.service.impl.CommentServiceImpl;
import cu.sitrans.asktravel.service.impl.QuestionServiceImpl;
import cu.sitrans.asktravel.service.impl.ReactServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reactions")
public class ReactionController implements InitializingBean {

    @Autowired
    ReactionService reactionService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    AnswerServiceImpl answerService;

    @Autowired
    private ModelMapper modelMapper;

    private Map<PostTypes, ReactServiceImpl> map;

    @Override
    public void afterPropertiesSet() throws Exception {
        map = new HashMap<>();
        map.put(PostTypes.COMMENT, commentService);
        map.put(PostTypes.ANSWER, answerService);
        map.put(PostTypes.QUESTION, questionService);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ReactionDTO reactionDTO) {
        PostTypes postType = Arrays.stream(PostTypes.values()).filter(postTypes -> postTypes.name().equals(reactionDTO.getType().toUpperCase())).findFirst().get();

        return ResponseHandler.generateResponse("Registro realizado satisfactoriamente", HttpStatus.OK,
                map.get(postType).react(reactionDTO.getObjectId(), modelMapper.map(reactionDTO, Reaction.class)));

    }

    @GetMapping("/{id}/{type}")
    public ResponseEntity<?> get(@PathVariable("id") String id, @PathVariable("type") String type) {
        return ResponseHandler.generateResponse("Registro realizado satisfactoriamente", HttpStatus.OK,
                reactionService.getReaction(id, type));
    }
}

