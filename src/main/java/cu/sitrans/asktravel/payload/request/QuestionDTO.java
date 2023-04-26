package cu.sitrans.asktravel.payload.request;

import cu.sitrans.asktravel.models.Answer;
import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class QuestionDTO {

    private String id;

    private User user;

    @NotBlank
    @Size(min = 10, message = "Por favor formule mejor el título de su pregunta, es muy corta")
    private String title;

    @NotBlank
    @Size(min = 10, message = "Por favor formule mejor su pregunta, es muy corta")
    private String description;

    private Answer answer;

    private Catalog statusCat;

    private List<Catalog> tags;

    private List<Catalog> status;

    private String follower;

    private Catalog category;

    private MultipartFile multipartFile;
}
