// ProductService class in Quarkus
@Quarkus
@ApplicationScoped
public class ProductService {

    @Inject
    private CatalogService catalogService;

    public List<Product> getProducts() {
        return catalogService.getCatalogItems().stream()
            .map(entity -> toQuarkusProduct(entity))
            .collect(Collectors.toList());
    }

    public Product getProductByItemId(String itemId) {
        CatalogItemEntity entity = catalogService.getCatalogItemById(itemId);
        if (entity == null)
            return null;

        // Return the entity
        return toQuarkusProduct(entity);
    }

    private Product toQuarkusProduct(CatalogItemEntity entity) {
        return new Product(entity.getId(), entity.getProduct().getName(), entity.getProduct().getDescription());
    }

    public Product(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}