// Update the code as follows
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.model.ShoppingCartItem;
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.service.ProductService;
import com.redhat.coolstore.service.PromoService;
import com.redhat.coolstore.service.ShippingService;
import com.redhat.coolstore.service.ShoppingCartService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ShoppingCartService {

    @EJB
    private ProductService productService;

    @EJB
    private PromoService promoService;

    @EJB
    private ShippingService shippingService;

    private Logger log;

    private ShoppingCart cart = new ShoppingCart();

    public ShoppingCart getShoppingCart(String cartId) {
        return cart;
    }

    public ShoppingCart checkOutShoppingCart(String cartId) {
        ShoppingCart cart = this.getShoppingCart(cartId);

        log.info("Sending order: ");
        shoppingCartOrderProcessor.process(cart);

        cart.resetShoppingCartItemList();
        priceShoppingCart(cart);
        return cart;
    }

    public void priceShoppingCart(ShoppingCart sc) {

        if (sc != null) {

            initShoppingCartForPricing(sc);

            if (sc.getShoppingCartItemList() != null && sc.getShoppingCartItemList().size() > 0) {

                List<ShoppingCartItem> sciList = sc.getShoppingCartItemList().stream().collect(Collectors.toList());

                for (ShoppingCartItem sci : sciList) {

                    sci.setProduct(productService.getProductByItemId(sci.getProduct().getItemId()));
                    sci.setPrice(sci.getProduct().getPrice());

                    sci.setPromoSavings(promoService.applyCartItemPromotions(sci));

                    sci.setCartItemTotal(sci.getPromoSavings() + sci.getPrice() * sci.getQuantity());
                }

                sc.setShippingTotal(shippingService.calculateShipping(sc));

                if (sc.getCartItemTotal() >= 25) {
                    sc.setShippingTotal(sc.getShippingTotal() + shippingService.calculateShippingInsurance(sc));
                }

            }

            promoService.applyShippingPromotions(sc);

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
            sci.setProduct(productService.getProductByItemId(sci.getProduct().getItemId()));
            sci.setPrice(sci.getProduct().getPrice());
            sci.setPromoSavings(0);
        }

    }
}