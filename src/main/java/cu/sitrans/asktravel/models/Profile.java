package cu.sitrans.asktravel.models;

import cu.sitrans.asktravel.models.generic.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter

@Document(collection = "profiles")
public class Profile {

    @Id
    private String id;

    @NotBlank
    @Size(min = 4, max = 20)
    private String nickname;

    @NotBlank
    @Size(min = 4, max = 20)
    private String displayName;

    private MultipartFile multipartFile;

    private String fileEncode;

    @DBRef
    private File avatar;

    @DBRef
    private File cover;

    public Profile (String nickname, String displayName){
        this.nickname = nickname;
        this.displayName = displayName;
    }
}