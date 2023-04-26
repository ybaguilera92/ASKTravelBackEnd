package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.payload.request.CatalogListDTO;
import cu.sitrans.asktravel.repositories.CatalogRepository;
import cu.sitrans.asktravel.service.strategies.CatalogGetStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CatalogTableIdStrategy implements CatalogGetStrategy {

    @Autowired
    CatalogRepository catalogRepository;

    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {
        CatalogListDTO catalogListDTO = (CatalogListDTO) baseDTO;
        Pageable pageable = PageRequest.of(catalogListDTO.getPageIndex(), catalogListDTO.getPageSize());
        Page<Catalog> catalogs =  catalogRepository.findByIdTabla(catalogListDTO.getArg(), pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("catalogs", catalogs.getContent());
        response.put("currentPage", catalogs.getNumber());
        response.put("totalItems", catalogs.getTotalElements());
        response.put("totalPages", catalogs.getTotalPages());

        return response;
    }
}
