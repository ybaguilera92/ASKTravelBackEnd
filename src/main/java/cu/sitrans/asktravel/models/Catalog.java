package cu.sitrans.asktravel.models;

import cu.sitrans.asktravel.models.generic.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "catalog")
public class Catalog extends BaseEntity {

    private String idTabla;
    private String tablaArgumento;
    private String tablaDescription;
    private String tablaReferencia;
    private String status;

    public Catalog() {}

    public Catalog(String idTabla, String tablaArgumento, String tablaDescription, String tablaReferencia, String status) {
        this.idTabla = idTabla;
        this.tablaArgumento = tablaArgumento;
        this.tablaDescription = tablaDescription;
        this.tablaReferencia = tablaReferencia;
        this.status = status;
    }

    public String getIdTabla() {
        return idTabla;
    }

    public void setIdTabla(String idTabla) {
        this.idTabla = idTabla;
    }

    public String getTablaArgumento() {
        return tablaArgumento;
    }

    public void setTablaArgumento(String tablaArgumento) {
        this.tablaArgumento = tablaArgumento;
    }

    public String getTablaDescription() {
        return tablaDescription;
    }

    public void setTablaDescription(String tablaDescription) {
        this.tablaDescription = tablaDescription;
    }

    public String getTablaReferencia() {
        return tablaReferencia;
    }

    public void setTablaReferencia(String tablaReferencia) {
        this.tablaReferencia = tablaReferencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
