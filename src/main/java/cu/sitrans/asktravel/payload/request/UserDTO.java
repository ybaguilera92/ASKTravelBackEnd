package cu.sitrans.asktravel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String id;

    @NotBlank
    @Size(min = 3, max = 50)
    String name;

    @NotBlank
    @Size(min = 3, max = 50)
    String lastName;

    @NotBlank
    @Size(min = 5, max = 50)
    String userName;

    @NotBlank
    @Size(min = 10, max = 50)
    String email;

    @NotBlank
    String enabled;

    @NotEmpty
    String [] roles;


}
