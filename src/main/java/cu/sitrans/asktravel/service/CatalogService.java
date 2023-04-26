package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.models.types.CatalogListTypes;
import cu.sitrans.asktravel.payload.request.BaseDTO;

import java.util.Map;
import java.util.Optional;

public interface CatalogService {
    Catalog save(Catalog catalog);
    Catalog update(Catalog catalog);
    Optional<Catalog> getById (String id);
    Map<String, Object> getCatalogs(BaseDTO baseDTO, CatalogListTypes type);
    /*Map<String, Object> list(Integer pageNo, Integer pageSize);
    Map<String, Object> getCatalogsMultiple(String [] idTabla );*/
}
