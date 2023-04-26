package cu.sitrans.asktravel.payload.request;

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
public class UserCreateDTO extends UserDTO{

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

}
