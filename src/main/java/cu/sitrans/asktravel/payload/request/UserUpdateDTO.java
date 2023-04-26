package cu.sitrans.asktravel.payload.request;

import cu.sitrans.asktravel.models.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO extends UserDTO{


    private String fileEncode;
    private File avatar;
    private File cover;

}
