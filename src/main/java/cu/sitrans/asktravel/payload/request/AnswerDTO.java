package cu.sitrans.asktravel.payload.request;

import cu.sitrans.asktravel.models.Comment;
import cu.sitrans.asktravel.models.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AnswerDTO extends Post {

    private String id;
    @NotNull
    private String title;
    private String excerpt;
    @NotNull
    private String description;
    boolean best;
    private List<Comment> comments;

}
