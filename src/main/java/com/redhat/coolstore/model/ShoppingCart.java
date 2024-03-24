// Update the import statements
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.SessionScoped;

// Update the `Dependent` type to `SessionScoped`
@SessionScoped
public class ShoppingCart implements Serializable {

    // Update the import statements for the getter and setter methods
    private static final long serialVersionUID = -1108043957592113528L;

    // Update the field declaration
    private double cartItemTotal;

    // Update the field declaration
    private double cartItemPromoSavings;

    // Update the field declaration
    private double shippingTotal;

    // Update the field declaration
    private double shippingPromoSavings;

    // Update the field declaration
    private double cartTotal;

    // Update the field declaration
    private List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();

    // Update the constructor
    public ShoppingCart() {

    }

    // Update the getter method for the `shoppingCartItemList` field
    @Override
    public String toString() {
        return "ShoppingCart [cartItemTotal=" + cartItemTotal + ", cartItemPromoSavings=" + cartItemPromoSavings + ", shippingTotal=" + shippingTotal + ", shippingPromoSavings=" + shippingPromoSavings + ", cartTotal=" + cartTotal + ", shoppingCartItemList=" + shoppingCartItemList + "]";
    }

    // Update the setter method for the `shoppingCartItemList` field
    public void setShoppingCartItemList(List<ShoppingCartItem> shoppingCartItemList) {
        this.shoppingCartItemList = shoppingCartItemList;
    }

    // Update the `addShoppingCartItem` method
    public void addShoppingCartItem(ShoppingCartItem sci) {
        if (sci != null) {
            shoppingCartItemList.add(sci);
        }
    }

    // Update the `removeShoppingCartItem` method
    public boolean removeShoppingCartItem(ShoppingCartItem sci) {
        boolean removed = false;
        if (sci != null) {
            removed = shoppingCartItemList.remove(sci);
        }
        return removed;
    }

    // Update the getter method for the `cartItemTotal` field
    public double getCartItemTotal() {
        return cartItemTotal;
    }

    // Update the setter method for the `cartItemTotal` field
    public void setCartItemTotal(double cartItemTotal) {
        this.cartItemTotal = cartItemTotal;
    }

    // Update the getter method for the `shippingTotal` field
    public double getShippingTotal() {
        return shippingTotal;
    }

    // Update the setter method for the `shippingTotal` field
    public void setShippingTotal(double shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    // Update the getter method for the `cartTotal` field
    public double getCartTotal() {
        return cartTotal;
    }

    // Update the setter method for the `cartTotal` field
    public void setCartTotal(double cartTotal) {
        this.cartTotal = cartTotal;
    }

    // Update the getter method for the `cartItemPromoSavings` field
    public double getCartItemPromoSavings() {
        return cartItemPromoSavings;
    }

    // Update the setter method for the `cartItemPromoSavings` field
    public void setCartItemPromoSavings(double cartItemPromoSavings) {
        this.cartItemPromoSavings = cartItemPromoSavings;
    }

    // Update the getter method for the `shippingPromoSavings` field
    public double getShippingPromoSavings() {
        return shippingPromoSavings;
    }

    // Update the setter method for the `shippingPromoSavings` field
    public void setShippingPromoSavings(double shippingPromoSavings) {
        this.shippingPromoSavings = shippingPromoSavings;
    }
}