package cu.sitrans.asktravel.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Email {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
