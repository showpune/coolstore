// Write the updated file for Quarkus in this section

@QuarkusEntity
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = -1108043957592113528L;

    @Id
    private String id;

    private double cartItemTotal;

    private double cartItemPromoSavings;

    private double shippingTotal;

    private double shippingPromoSavings;

    private double cartTotal;

    private List<ShoppingCartItem> shoppingCartItemList;

    public ShoppingCart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCartItemTotal() {
        return cartItemTotal;
    }

    public void setCartItemTotal(double cartItemTotal) {
        this.cartItemTotal = cartItemTotal;
    }

    public double getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(double shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public double getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(double cartTotal) {
        this.cartTotal = cartTotal;
    }

    public double getCartItemPromoSavings() {
        return cartItemPromoSavings;
    }

    public void setCartItemPromoSavings(double cartItemPromoSavings) {
        this.cartItemPromoSavings = cartItemPromoSavings;
    }

    public double getShippingPromoSavings() {
        return shippingPromoSavings;
    }

    public void setShippingPromoSavings(double shippingPromoSavings) {
        this.shippingPromoSavings = shippingPromoSavings;
    }

    @Override
    public String toString() {
        return "ShoppingCart [cartItemTotal=" + cartItemTotal + ", cartItemPromoSavings=" + cartItemPromoSavings + ", shippingTotal=" + shippingTotal + ", shippingPromoSavings=" + shippingPromoSavings + ", cartTotal=" + cartTotal + ", shoppingCartItemList=" + shoppingCartItemList + "]";
    }
}