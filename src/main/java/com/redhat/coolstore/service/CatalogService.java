import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.mutiny.session.Mutiny;
import org.hibernate.reactive.mutiny.session.MutinySession;
import org.hibernate.reactive.mutiny.session.MutinySessionFactory;
import org.hibernate.reactive.session.Session;
import org.hibernate.reactive.session.SessionFactory;
import org.hibernate.reactive.util.impl.CompletionStages;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.redhat.coolstore.model.CatalogItem;
import com.redhat.coolstore.model.CatalogItemEntity;

@ApplicationScoped
public class CatalogService {

    private static final Logger log = LoggerFactory.getLogger(CatalogService.class);

    @Inject
    MutinySessionFactory<Session> sessionFactory;

    public Uni<List<CatalogItemEntity>> getCatalogItems() {
        return CatalogItemEntity.listAll();
    }
}