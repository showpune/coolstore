// Write the updated file for Quarkus here

@Quarkus
@Path("/cart")
public class CartEndpoint {

    private final ShoppingCartService shoppingCartService;

    @Inject
    public CartEndpoint(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Get(value = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ShoppingCart> getCart(@PathParam("cartId") String cartId) {
        return shoppingCartService.getShoppingCart(cartId);
    }

    @Post(value = "/checkout/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> checkout(@PathParam("cartId") String cartId) {
        return shoppingCartService.checkOutShoppingCart(cartId);
    }

    @Post(value = "/{cartId}/{itemId}/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> add(@PathParam("cartId") String cartId,
                            @PathParam("itemId") String itemId,
                            @PathParam("quantity") int quantity) {
        ShoppingCart cart = shoppingCartService.getShoppingCart(cartId);

        Product product = shoppingCartService.getProduct(itemId);

        ShoppingCartItem sci = new ShoppingCartItem.Builder()
                .setProduct(product)
                .setQuantity(quantity)
                .setPrice(product.getPrice())
                .build();

        cart.addShoppingCartItem(sci);

        return shoppingCartService.priceShoppingCart(cart);
    }

    @Post(value = "/{cartId}/{tmpId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> set(@PathParam("cartId") String cartId,
                            @PathParam("tmpId") String tmpId) {
        ShoppingCart cart = shoppingCartService.getShoppingCart(cartId);
        ShoppingCart tmpCart = shoppingCartService.getShoppingCart(tmpId);

        if (tmpCart != null) {
            cart.resetShoppingCartItemList();
            cart.setShoppingCartItemList(tmpCart.getShoppingCartItemList());
        }

        return shoppingCartService.priceShoppingCart(cart);
    }

    @Delete(value = "/{cartId}/{itemId}/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> delete(@PathParam("cartId") String cartId,
                               @PathParam("itemId") String itemId,
                               @PathParam("quantity") int quantity) {
        List<ShoppingCartItem> toRemoveList = new ArrayList<>();

        ShoppingCart cart = shoppingCartService.getShoppingCart(cartId);

        cart.getShoppingCartItemList().stream()
                .filter(sci -> sci.getProduct().getItemId().equals(itemId))
                .forEach(sci -> {
                    if (quantity >= sci.getQuantity()) {
                        toRemoveList.add(sci);
                    } else {
                        sci.setQuantity(sci.getQuantity() - quantity);
                    }
                });

        toRemoveList.forEach(cart::removeShoppingCartItem);

        return shoppingCartService.priceShoppingCart(cart);
    }
}