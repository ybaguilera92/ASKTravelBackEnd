package cu.sitrans.asktravel.payload.request;

import cu.sitrans.asktravel.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {

    private String id;

    String type;

    @NotBlank
    String objectId;

    private User user;

    @NotBlank
    @Size(min = 10, message = "Por favor formule mejor su comentario")
    private String content;

}
