package cu.sitrans.asktravel.payload.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReactionDTO {

    String id;

    String type;

    @NotBlank
    String objectId;

    @NotNull
    Boolean reaction;

}
