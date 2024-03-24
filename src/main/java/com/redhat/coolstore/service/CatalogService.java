@QuarkusApplication(name = "coolstore-web")
@ResteasyClient
public class CatalogService {

    @Inject
    @ApplicationScoped
    private Catalog catalog;

    @Inject
    @ApplicationScoped
    private List<CatalogItem> catalogItems;

    // Add other methods and fields as needed

    // Add @PostConstruct and @PreDestroy methods to manage lifecycle of CDI beans
    @PostConstruct
    public void init() {
        // Add code to initialize CDI beans
    }

    @PreDestroy
    public void destroy() {
        // Add code to destroy CDI beans
    }
}