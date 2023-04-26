package cu.sitrans.asktravel.service.impl;

import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.models.*;
import cu.sitrans.asktravel.models.types.PostTypes;
import cu.sitrans.asktravel.repositories.AnswerRepository;
import cu.sitrans.asktravel.repositories.CommentRepository;
import cu.sitrans.asktravel.repositories.QuestionRepository;
import cu.sitrans.asktravel.repositories.ReactionRepository;
import cu.sitrans.asktravel.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Reaction> getReactions() {
        return reactionRepository.findAll();
    }

    @Override
    public long getReaction(String id, String type) {
        List<Reaction> reactions = new ArrayList<>();
        PostTypes postType = Arrays.stream(PostTypes.values()).filter(postTypes -> postTypes.name().equals(type.toUpperCase())).findFirst().get();

        switch (postType) {
            case QUESTION:
                reactions = questionRepository.findById(id).
                        orElseThrow(() -> new EntityNotFoundException(Question.class, "id", id.toString())).getReactions();
                break;

            case ANSWER:
                reactions = answerRepository.findById(id).
                        orElseThrow(() -> new EntityNotFoundException(Answer.class, "id", id.toString())).getReactions();
                break;

            case COMMENT:
                reactions = commentRepository.findById(id).
                        orElseThrow(() -> new EntityNotFoundException(Comment.class, "id", id.toString())).getReactions();
                break;
        }

        return reactions.stream().filter(reaction -> reaction.getReaction()).count() - reactions.stream().filter(reaction -> !reaction.getReaction()).count();
    }
}
