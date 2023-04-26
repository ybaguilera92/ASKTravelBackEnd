package cu.sitrans.asktravel.service.impl;

import cu.sitrans.asktravel.factory.CatalogListStrategyFactory;
import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.models.types.CatalogListTypes;
import cu.sitrans.asktravel.payload.request.BaseDTO;
import cu.sitrans.asktravel.repositories.CatalogRepository;
import cu.sitrans.asktravel.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    CatalogListStrategyFactory catalogListStrategyFactory;

    @Override
    public Catalog save(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Override
    public Catalog update(Catalog catalog) {
        if (catalogRepository.findById(catalog.getId()).isPresent()) {
            Catalog existingCatalog = catalogRepository.findById(catalog.getId()).get();
            existingCatalog.setTablaArgumento(catalog.getTablaArgumento());
            existingCatalog.setTablaDescription(catalog.getTablaDescription());
            existingCatalog.setTablaReferencia(catalog.getTablaReferencia());
            existingCatalog.setStatus(catalog.getStatus());

            return catalogRepository.save(existingCatalog);
        } else {
            return null;
        }
    }

    @Override
    public Optional<Catalog> getById(String id) {
        return catalogRepository.findById(id);
    }


    public Map<String, Object> getCatalogs(BaseDTO baseDTO, CatalogListTypes type){
        return catalogListStrategyFactory.getStrategy(type).getList(baseDTO);
    }
}
