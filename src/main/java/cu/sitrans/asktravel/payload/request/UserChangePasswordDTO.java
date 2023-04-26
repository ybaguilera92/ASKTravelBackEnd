package cu.sitrans.asktravel.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UserChangePasswordDTO extends UserUpdateDTO{
    @NotBlank
    @Size(max = 120)
//    @JsonIgnore
    private String password;

}
