package cu.sitrans.asktravel.service.impl;

import cu.sitrans.asktravel.exception.EntityNotFoundException;
import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.models.Comment;
import cu.sitrans.asktravel.models.Question;
import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.repositories.AnswerRepository;
import cu.sitrans.asktravel.repositories.CommentRepository;
import cu.sitrans.asktravel.repositories.QuestionRepository;
import cu.sitrans.asktravel.repositories.generic.BaseRepository;
import cu.sitrans.asktravel.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl extends ReactServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    CommentServiceImpl(CommentRepository commentRepository) {
        super(commentRepository);
    }

    @Override
    public Answer saveCommentAnswer(String idAnswer, Comment comment) {

        boolean followerFound = false;
        Answer answer = answerRepository.findById(idAnswer).
                orElseThrow(() -> new EntityNotFoundException(Answer.class, "id", idAnswer.toString()));

        List<Comment> comments = answer.getComments();

        // tree Name(answer id)
        comment.setCommentId(answer.getId());
        comment.setParent(null);
        comment.setReactions(Collections.EMPTY_LIST);
        comment.setDescendants(Collections.EMPTY_LIST);
        Comment commentSaved = commentRepository.save(comment);
        if (comments == null) {
            comments = new ArrayList<>();
            comments.add(commentSaved);
            answer.setComments(comments);
        } else {
            answer.getComments().add(commentSaved);
        }
        Answer answerSaved = answerRepository.save(answer);

        List<Answer> answersToFind = new ArrayList<>();
        answersToFind.add(answerSaved);

        Question question = questionRepository.findByAnswersIn(answersToFind).
                orElseThrow(() -> new EntityNotFoundException(Answer.class, "id", idAnswer.toString()));

        for ( User follower : question.getFollowers()){
            if(follower.getId().equals(commentSaved.getUser().getId())){
                followerFound = true;
                break;
            }
        }

        if(!followerFound){
            question.getFollowers().add(commentSaved.getUser());
            questionRepository.save(question);
        }

        return answerSaved;
    }

    @Override
    public Comment saveComment(String idComment, Comment comment) {

        boolean followerFound = false;
        Comment parentComment = commentRepository.findById(idComment).
                orElseThrow(() -> new EntityNotFoundException(Comment.class, "id", idComment.toString()));

        comment.setCommentId(parentComment.getCommentId());
        comment.setParent(parentComment.getId());
        comment.setReactions(Collections.EMPTY_LIST);
        comment.setDescendants(Collections.EMPTY_LIST);
        Comment commentSaved = commentRepository.save(comment);

        parentComment.getDescendants().add(commentSaved);

        String answerId = commentSaved.getCommentId();
        Answer answer = answerRepository.findById(commentSaved.getCommentId()).
                orElseThrow(() -> new EntityNotFoundException(Answer.class, "id", answerId.toString()));

        List<Answer> answersToFind = new ArrayList<>();
        answersToFind.add(answer);
        Question question = questionRepository.findByAnswersIn(answersToFind).
                orElseThrow(() -> new EntityNotFoundException(Answer.class, "id", answerId.toString()));

        for ( User follower : question.getFollowers()){
            if(follower.getId().equals(commentSaved.getUser().getId())){
                followerFound = true;
                break;
            }
        }

        if(!followerFound){
            question.getFollowers().add(commentSaved.getUser());
            questionRepository.save(question);
        }

        return commentRepository.save(parentComment);
    }
}
