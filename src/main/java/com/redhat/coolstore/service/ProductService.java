// Add Quarkus annotations and dependencies here
import io.quarkus.arc.Arc;
import io.quarkus.hibernate.orm.PersistenceUnit;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class ProductService {

    @Inject
    private CatalogService cm;

    @PersistenceContext
    private EntityManager em;

    public List<Product> getProducts() {
        return cm.getCatalogItems().stream().map(entity -> toProduct(entity)).collect(Collectors.toList());
    }

    public Product getProductByItemId(String itemId) {
        CatalogItemEntity entity = cm.getCatalogItemById(itemId);
        if (entity == null)
            return null;

        // Return the entity
        return Transformers.toProduct(entity);
    }
}
