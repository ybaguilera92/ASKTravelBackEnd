package cu.sitrans.asktravel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionListDTO extends BaseDTO {
    String status;
    String phrase;
    String tag;
}
