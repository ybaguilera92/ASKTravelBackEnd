package cu.sitrans.asktravel.models;

import cu.sitrans.asktravel.models.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "reactions")
public class Reaction extends BaseEntity {

    Boolean reaction;
}
