// Update the code as follows
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.redhat.coolstore.model.Promotion;
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.service.PromoService;

@ApplicationScoped
public class PromoService {

    private String name;
    private Set<Promotion> promotionSet;
    private ShoppingCart shoppingCart;

    public PromoService() {
        name = "PromoService";
        promotionSet = new HashSet<>();
        promotionSet.add(new Promotion("329299", 0.25));
        shoppingCart = new ShoppingCart();
    }

    @Produces
    public Map<String, Promotion> getPromotions() {
        return new HashMap<>(promotionSet);
    }

    public void applyCartItemPromotions(ShoppingCart shoppingCart) {
        if (shoppingCart != null && shoppingCart.getShoppingCartItemList().size() > 0) {
            Map<String, Promotion> promoMap = getPromotions();
            for (ShoppingCartItem sci : shoppingCart.getShoppingCartItemList()) {
                String productId = sci.getProduct().getItemId();
                Promotion promo = promoMap.get(productId);
                if (promo != null) {
                    sci.setPromoSavings(sci.getProduct().getPrice() * promo.getPercentOff() * -1);
                    sci.setPrice(sci.getProduct().getPrice() * (1 - promo.getPercentOff()));
                }
            }
        }
    }

    public void applyShippingPromotions(ShoppingCart shoppingCart) {
        if (shoppingCart != null && shoppingCart.getCartItemTotal() >= 75) {
            shoppingCart.setShippingPromoSavings(shoppingCart.getShippingTotal() * -1);
            shoppingCart.setShippingTotal(0);
        }
    }

    public void setPromotions(Set<Promotion> promotionSet) {
        if (promotionSet != null) {
            this.promotionSet = new HashSet<>(promotionSet);
        }
    }

    @Override
    public String toString() {
        return "PromoService [name=" + name + ", promotionSet=" + promotionSet + "]";
    }
}