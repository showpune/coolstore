@ApplicationScoped
@Path("/cart")
public class CartEndpoint {

    private static final long serialVersionUID = -7227732980791688773L;

    @Inject
    private Service<ShoppingCartService> shoppingCartService;

    @Get(value = "/{cartId}")
    public ShoppingCart get(@PathParam("cartId") String cartId) {
        return shoppingCartService.get().getShoppingCart(cartId);
    }

    @Post(value = "/checkout/{cartId}")
    public ShoppingCart checkout(@PathParam("cartId") String cartId) {
        return shoppingCartService.get().checkOutShoppingCart(cartId);
    }

    @Put(value = "/{cartId}/{itemId}/{quantity}")
    public ShoppingCart add(@PathParam("cartId") String cartId,
                            @PathParam("itemId") String itemId,
                            @PathParam("quantity") Long quantity) {
        ShoppingCart cart = shoppingCartService.get().getShoppingCart(cartId);

        Product product = shoppingCartService.getProduct(itemId);

        ShoppingCartItem sci = new ShoppingCartItem();
        sci.setProduct(product);
        sci.setQuantity(quantity);
        sci.setPrice(product.getPrice());
        cart.addShoppingCartItem(sci);

        try {
            shoppingCartService.price(cart);
            cart.setShoppingCartItemList(cart.getShoppingCartItemList().toList());
        } catch (Exception ex) {
            cart.removeShoppingCartItem(sci);
            throw ex;
        }

        return cart;
    }

    @Delete(value = "/{cartId}/{itemId}/{quantity}")
    public ShoppingCart delete(@PathParam("cartId") String cartId,
                               @PathParam("itemId") String itemId,
                               @PathParam("quantity") Long quantity) {

        List<ShoppingCartItem> toRemoveList = new ArrayList<>();

        ShoppingCart cart = shoppingCartService.get().getShoppingCart(cartId);

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

        shoppingCartService.price(cart);
        return cart;
    }
}