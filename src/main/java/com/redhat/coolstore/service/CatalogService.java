@ApplicationScoped
public class CatalogService implements CatalogServiceInterface {

    @Inject
    private ExtendedContext extendedContext;

    @Override
    public List<Product> getAllProducts() {
        // Add code to fetch products from the database here
        // ...
    }
}