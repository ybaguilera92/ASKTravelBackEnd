package cu.sitrans.asktravel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cu.sitrans.asktravel.models.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter


@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    @JsonIgnore
    private String password;

    @NotBlank
    private boolean enabled;

    @NotBlank
    @Size(max = 64)
    private String verificationCode;

    private MultipartFile multipartFilea;


    private String fileEncode;

    @DBRef
    private File avatar;




    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User() {
      this.enabled = false;
    }

    public User(String name,String lastName,String userName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.username = userName;
        this.email = email;
        this.password = password;
        this.enabled = false;
    }

}