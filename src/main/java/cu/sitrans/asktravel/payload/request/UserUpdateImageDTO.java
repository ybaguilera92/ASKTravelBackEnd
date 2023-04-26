package cu.sitrans.asktravel.payload.request;

import cu.sitrans.asktravel.models.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateImageDTO extends UserUpdateDTO {

//    @DBRef
//    private File avatar;
    private MultipartFile multipartFilea;
}
