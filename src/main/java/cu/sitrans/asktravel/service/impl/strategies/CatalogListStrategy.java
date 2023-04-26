package cu.sitrans.asktravel.service.impl.strategies;

import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.repositories.CatalogRepository;
import cu.sitrans.asktravel.service.strategies.CatalogGetStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatalogListStrategy implements CatalogGetStrategy {

    @Autowired
    CatalogRepository catalogRepository;

    @Override
    public Map<String, Object> getList(BaseDTO baseDTO) {
        Pageable pageable = PageRequest.of(baseDTO.getPageIndex(), baseDTO.getPageSize());
        List<Catalog> catalogs =  catalogRepository.findAll();
        Set<String> seen = new HashSet<>();

        catalogs.removeIf(c->!seen.add(c.getIdTabla()));

        Page page = new PageImpl<>(catalogs, pageable, catalogs.size());

        Map<String, Object> response = new HashMap<>();
        response.put("catalogs", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return response;
    }
}
