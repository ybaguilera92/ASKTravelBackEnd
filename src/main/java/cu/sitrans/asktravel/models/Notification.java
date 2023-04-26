package cu.sitrans.asktravel.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;

    private String message;

    private String type;
    
    private String params;

    //@CreatedDate
    private String createdDate;

   // @LastModifiedDate
    private String lastModifiedDate;
}
