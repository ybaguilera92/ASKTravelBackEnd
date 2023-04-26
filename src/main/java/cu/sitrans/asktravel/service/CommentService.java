package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.models.Comment;


public interface CommentService {
    Answer saveCommentAnswer(String idAnswer, Comment comment);
    Comment saveComment(String idComment, Comment comment);
}
