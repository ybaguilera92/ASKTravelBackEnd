package cu.sitrans.asktravel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CatalogDTO {

    private String id;

    @NotBlank
    String idTabla;

    @NotBlank
    String tablaArgumento;

    @NotBlank
    String tablaDescription;

    String tablaReferencia;

    @NotBlank
    String status;
}
