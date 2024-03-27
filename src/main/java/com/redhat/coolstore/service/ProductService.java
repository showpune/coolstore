// Update the ProductService class to use Quarkus and CDI
@Quarkus
@ApplicationScoped
public class ProductService {

    @Inject
    private CatalogService catalogService;

    public List<Product> getProducts() {
        return catalogService.getCatalogItems().stream().map(entity -> toProduct(entity)).collect(Collectors.toList());
    }

    public Product getProductByItemId(String itemId) {
        CatalogItemEntity entity = catalogService.getCatalogItemById(itemId);
        if (entity == null)
            return null;

        // Return the entity
        return Transformers.toProduct(entity);
    }
}