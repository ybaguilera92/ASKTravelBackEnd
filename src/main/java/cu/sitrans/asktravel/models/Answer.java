package cu.sitrans.asktravel.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Document(collection = "answers")
public class Answer extends Post {

    @TextIndexed
    private String title;
    private String excerpt;
    private String parent;

    @TextIndexed
    private String description;

    boolean best;

    @DBRef
    private List<Comment> comments;

    public Answer(String title, String description) {
        this.title = title;
        this.excerpt = description.substring(0, 50).toString(). concat("...");
        this.description = description;
    }
}
