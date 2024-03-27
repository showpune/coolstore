import jakarta.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

    @PersistenceContext
    EntityManager em;

    // Rest of the code
}