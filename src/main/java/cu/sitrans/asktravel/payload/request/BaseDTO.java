package cu.sitrans.asktravel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseDTO {
    int pageIndex = 0;
    int pageSize = 5;
    String type;
}
