// Add the following import statements at the top of the file:
import jakarta.ejb.EJB;
import jakarta.inject.Inject;

// Update the import statements on lines 6 and 7:
import javax.ejb.Stateless;
import javax.inject.Provider;

// Update the class declaration on line 5:
@Stateless
public class ShoppingCartService {

// Update the methods and fields as follows:
    @Inject
    private Logger log;

    @Inject
    private ProductService productServices;

    @Inject
    private PromoService ps;

    @Inject
    private ShoppingCartOrderProcessor shoppingCartOrderProcessor;

    private ShoppingCart cart = new ShoppingCart();

    // Update the constructor on line 9:
    public ShoppingCartService() {
    }

    // Update the getShoppingCart method on line 12:
    @EJB
    public ShoppingCart getShoppingCart(@javax.inject.Qualifier("cartId") String cartId) {
        return cart;
    }

    // Update the checkOutShoppingCart method on lines 15-19:
    public ShoppingCart checkOutShoppingCart(@javax.inject.Qualifier("cartId") String cartId) {
        ShoppingCart cart = this.getShoppingCart(cartId);

        log.info("Sending order: ");
        shoppingCartOrderProcessor.process(cart);

        cart.resetShoppingCartItemList();
        priceShoppingCart(cart);
        return cart;
    }

    // Update the priceShoppingCart method on lines 22-31:
    public void priceShoppingCart(ShoppingCart sc) {

        if (sc != null) {

            initShoppingCartForPricing(sc);

            if (sc.getShoppingCartItemList() != null && sc.getShoppingCartItemList().size() > 0) {

                ps.applyCartItemPromotions(sc);

                for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {

                    sc.setCartItemPromoSavings(
                            sc.getCartItemPromoSavings() + sci.getPromoSavings() * sci.getQuantity());
                    sc.setCartItemTotal(sc.getCartItemTotal() + sci.getPrice() * sci.getQuantity());

                }

                sc.setShippingTotal(lookupShippingServiceRemote().calculateShipping(sc));

                if (sc.getCartItemTotal() >= 25) {
                    sc.setShippingTotal(sc.getShippingTotal()
                            + lookupShippingServiceRemote().calculateShippingInsurance(sc));
                }

            }

            ps.applyShippingPromotions(sc);

            sc.setCartTotal(sc.getCartItemTotal() + sc.getShippingTotal());

        }

    }

    // Update the initShoppingCartForPricing method on lines 35-39:
    private void initShoppingCartForPricing(ShoppingCart sc) {

        sc.setCartItemTotal(0);
        sc.setCartItemPromoSavings(0);
        sc.setShippingTotal(0);
        sc.setShippingPromoSavings(0);
        sc.setCartTotal(0);

        for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {
            Product p = getProduct(sci.getProduct().getItemId());
            //if product exist
            if (p != null) {
                sci.setProduct(p);
                sci.setPrice(p.getPrice());
            }

            sci.setPromoSavings(0);
        }

    }

    // Update the getProduct method on line 45:
    @EJB
    public Product getProduct(@javax.inject.Qualifier("itemId") String itemId) {
        return productServices.getProductByItemId(itemId);
    }

	// Update the lookupShippingServiceRemote method on line 49:
    @javax.inject.Singleton
    private ShippingServiceRemote lookupShippingServiceRemote() {
        try {
            final Hashtable<String, String> jndiProperties = new Hashtable<>();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");

            final Context context = new InitialContext(jndiProperties);

            return (ShippingServiceRemote) context.lookup("ejb:/ROOT/ShippingService!" + ShippingServiceRemote.class.getName());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
