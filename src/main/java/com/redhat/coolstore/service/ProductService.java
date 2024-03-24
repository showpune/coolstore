// ProductService.java
import io.quarkus.arc.Arc;
import io.quarkus.hibernate.orm.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import com.redhat.coolstore.model.CatalogItemEntity;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.utils.Transformer;
import io.quarkus.hibernate.orm.runtime.SessionContext;

@ApplicationScoped
public class ProductService {

    private static final Logger log = Logger.getLogger(ProductService.class);

    @Inject
    private SessionFactory sessionFactory;

    @Inject
    private CatalogService catalogService;

    public List<Product> getProducts() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CatalogItemEntity.class);
        criteria.add(Restrictions.eq("catalogId", catalogService.getCatalogId()));
        return criteria.list().stream()
            .map(entity -> Transformer.toProduct(entity))
            .collect(Collectors.toList());
    }

    public Optional<Product> getProductByItemId(String itemId) {
        CatalogItemEntity entity = catalogService.getCatalogItemById(itemId);
        if (entity == null) {
            log.debug("Product not found with id: {}", itemId);
            return Optional.empty();
        }

        return Optional.of(Transformer.toProduct(entity));
    }
}