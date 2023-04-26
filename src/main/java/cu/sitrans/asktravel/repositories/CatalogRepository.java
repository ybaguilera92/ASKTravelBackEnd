package cu.sitrans.asktravel.repositories;

import cu.sitrans.asktravel.models.Catalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CatalogRepository extends MongoRepository<Catalog, String> {
    public List<Catalog> findByIdTablaAndStatus(String idTabla, String status);
    public Page<Catalog> findByIdTabla(String idTabla, Pageable pageable);
    public List<Catalog> findByIdTabla(String idTabla);
    public Optional <Catalog> findByIdTablaAndTablaArgumento(String idTabla, String TablaArgumento);
    public Optional<Catalog> findById(String id);
}
