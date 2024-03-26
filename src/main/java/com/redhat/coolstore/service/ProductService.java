// Update the ProductService class to use @ApplicationScoped instead of @Stateless
@ApplicationScoped
public class ProductService {

    @Inject
    private CatalogService catalogService;

    @Inject
    private ProductMapper productMapper;

    public Mono<List<Product>> getProducts() {
        return catalogService.getCatalogItems()
            .map(items -> items.stream()
                .map(item -> productMapper.toProduct(item))
                .collect(Collectors.toList()));
    }

    public Mono<Product> getProductByItemId(String itemId) {
        return catalogService.getCatalogItemById(itemId)
            .flatMap(entity -> {
                if (entity == null) {
                    return Mono.empty();
                }
                return Mono.just(productMapper.toProduct(entity));
            });
    }
}