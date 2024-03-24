@QuarkusApplicationScoped
public class ShoppingCartService {

    @Inject
    private Logger log;

    @Inject
    private ProductService productServices;

    @Inject
    private PromoService ps;

    @Inject
    private ShoppingCartOrderProcessor shoppingCartOrderProcessor;

    private ShoppingCart cart = new ShoppingCart();

    @Getter
    public ShoppingCart getShoppingCart(String cartId) {
        return cart;
    }

    @PostConstruct
    public void init() {
        this.cart = new ShoppingCart();
    }

    @Getter
    public void checkoutShoppingCart(String cartId) {
        ShoppingCart cart = this.getShoppingCart(cartId);

        log.info("Sending order: ");
        shoppingCartOrderProcessor.process(cart);

        cart.resetShoppingCartItemList();
        priceShoppingCart(cart);
    }

    @Inject
    @Provides
    public ShippingServiceRemote shippingServiceRemote() {
        try {
            final Hashtable<String, String> jndiProperties = new Hashtable<>();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");

            final Context context = new InitialContext(jndiProperties);

            return (ShippingServiceRemote) context.lookup("ejb:/ROOT/ShippingService!" + ShippingServiceRemote.class.getName());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject
    @Autowired
    public void setProductServices(ProductService productServices) {
        this.productServices = productServices;
    }

    @Inject
    @Autowired
    public void setPromoService(PromoService ps) {
        this.ps = ps;
    }

    @Inject
    @Autowired
    public void setShoppingCartOrderProcessor(ShoppingCartOrderProcessor shoppingCartOrderProcessor) {
        this.shoppingCartOrderProcessor = shoppingCartOrderProcessor;
    }

    @Inject
    @Autowired
    public void setLogger(Logger log) {
        this.log = log;
    }

    public void priceShoppingCart(ShoppingCart sc) {
        if (sc != null) {
            initShoppingCartForPricing(sc);

            if (sc.getShoppingCartItemList() != null && sc.getShoppingCartItemList().size() > 0) {
                ps.applyCartItemPromotions(sc);

                for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {
                    sc.setCartItemPromoSavings(sc.getCartItemPromoSavings() + sci.getPromoSavings() * sci.getQuantity());
                    sc.setCartItemTotal(sc.getCartItemTotal() + sci.getPrice() * sci.getQuantity());
                }

                sc.setShippingTotal(shippingServiceRemote().calculateShipping(sc));

                if (sc.getCartItemTotal() >= 25) {
                    sc.setShippingTotal(sc.getShippingTotal() + shippingServiceRemote().calculateShippingInsurance(sc));
                }
            }

            ps.applyShippingPromotions(sc);

            sc.setCartTotal(sc.getCartItemTotal() + sc.getShippingTotal());
        }
    }

    private void initShoppingCartForPricing(ShoppingCart sc) {
        sc.setCartItemTotal(0);
        sc.setCartItemPromoSavings(0);
        sc.setShippingTotal(0);
        sc.setShippingPromoSavings(0);
        sc.setCartTotal(0);

        for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {
            Product p = productServices.getProductByItemId(sci.getProduct().getItemId());
            sci.setProduct(p);
            sci.setPrice(p.getPrice());
            sci.setPromoSavings(0);
        }
    }

    public Product getProduct(String itemId) {
        return productServices.getProductByItemId(itemId);
    }
}