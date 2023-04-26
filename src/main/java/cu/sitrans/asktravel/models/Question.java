package cu.sitrans.asktravel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Document(collection = "questions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Question extends Post {

    @JsonInclude()
    @Transient
    private Answer answer;

    @Transient
    private Catalog statusCat;

    @Transient
    private String follower;

    @DBRef
    private List<Answer> answers;

    @DBRef
    private List<Catalog> status;

    @DBRef
    private List<Catalog> tags;

    @DBRef
    private Catalog category;

    @TextIndexed
    private String title;

    @TextIndexed
    private String description;

    @TextScore
    private Float score;

    private MultipartFile multipartFile;

    private String fileEncode;

    private Integer visit;

    @DBRef
    private File file;

    @DBRef
    private List<User> followers;
}
