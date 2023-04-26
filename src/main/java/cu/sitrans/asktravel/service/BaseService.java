package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.models.types.CatalogListTypes;
import cu.sitrans.asktravel.payload.request.BaseDTO;

import java.util.Map;
import java.util.Optional;

public interface BaseService<T> {
    T save(T catalog);
    T update(T t);
    Optional<T> getById (String id);
    Map<String, Object> getList(BaseDTO baseDTO, CatalogListTypes type);
}
