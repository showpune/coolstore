@Path("/api/shoppingcart")
@Produces("application/json")
public class ShoppingCartService {

    @Inject
    private ProductService productServices;

    @Inject
    private PromoService ps;

    @Inject
    private ShippingServiceRemote remote;

    private ShoppingCart cart = new ShoppingCart();

    @Get("/{cartId}")
    public Response getShoppingCart(@PathParam("cartId") String cartId) {
        return Response.ok(cart).build();
    }

    @Post("/checkout/{cartId}")
    public Response checkOutShoppingCart(@PathParam("cartId") String cartId, @RequestBody ShoppingCart shoppingCart) {
        shoppingCartOrderProcessor.process(shoppingCart);

        cart.resetShoppingCartItemList();
        priceShoppingCart(shoppingCart);

        return Response.ok(shoppingCart).build();
    }

    @Put("/{cartId}")
    public Response updateShoppingCart(@PathParam("cartId") String cartId, @RequestBody ShoppingCart shoppingCart) {
        if (!authenticationPrincipal.isAuthenticated()) {
            return Response.unauthorized().build();
        }

        shoppingCartOrderProcessor.process(shoppingCart);

        cart.resetShoppingCartItemList();
        priceShoppingCart(shoppingCart);

        return Response.ok(shoppingCart).build();
    }

    @Delete("/{cartId}")
    public Response deleteShoppingCart(@PathParam("cartId") String cartId) {
        if (!authenticationPrincipal.isAuthenticated()) {
            return Response.unauthorized().build();
        }

        cart.resetShoppingCartItemList();
        priceShoppingCart(cart);

        return Response.noContent().build();
    }

    private void priceShoppingCart(ShoppingCart sc) {
        initShoppingCartForPricing(sc);

        if (sc.getShoppingCartItemList() != null && sc.getShoppingCartItemList().size() > 0) {
            ps.applyCartItemPromotions(sc);

            List<ShoppingCartItem> scil = sc.getShoppingCartItemList().stream()
                    .map(sci -> new ShoppingCartItem(sci.getProduct(), sci.getQuantity(), sci.getPrice()))
                    .collect(Collectors.toList());

            sc.setCartItemPromoSavings(sc.getCartItemPromoSavings() + scil.stream()
                    .mapToInt(sci -> sci.getPromoSavings() * sci.getQuantity()).sum());
            sc.setCartItemTotal(sc.getCartItemTotal() + scil.stream()
                    .mapToInt(sci -> sci.getPrice() * sci.getQuantity()).sum());

            sc.setShippingTotal(remote.calculateShipping(sc));

            if (sc.getCartItemTotal() >= 25) {
                sc.setShippingTotal(sc.getShippingTotal()
                         + remote.calculateShippingInsurance(sc));
            }

        }

        ps.applyShippingPromotions(sc);

        sc.setCartTotal(sc.getCartItemTotal() + sc.getShippingTotal());

    }

    private void initShoppingCartForPricing(ShoppingCart sc) {
        sc.setCartItemTotal(0);
        sc.setCartItemPromoSavings(0);
        sc.setShippingTotal(0);
        sc.setShippingPromoSavings(0);
        sc.setCartTotal(0);

        for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {
            Product p = productServices.getProductByItemId(sci.getProduct().getItemId());
            if (p != null) {
                sci.setProduct(p);
                sci.setPrice(p.getPrice());
            }

            sci.setPromoSavings(0);
        }

    }

    @Inject
    private AuthenticationPrincipal authenticationPrincipal;

    @Inject
    private ProductService productServices;

    @Inject
    private PromoService ps;

    @Inject
    private ShippingServiceRemote remote;

}