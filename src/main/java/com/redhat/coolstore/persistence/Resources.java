@Dependent
public class Resources {

    @PersistenceContext
    @ExtendedContext
    private EntityManager em;

    @Inject
    public EntityManager getEntityManager() {
        return em;
    }
}
