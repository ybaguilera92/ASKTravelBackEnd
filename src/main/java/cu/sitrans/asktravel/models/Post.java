package cu.sitrans.asktravel.models;

import cu.sitrans.asktravel.models.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post extends BaseEntity {

    @DBRef
    private List<Reaction> reactions;

}
