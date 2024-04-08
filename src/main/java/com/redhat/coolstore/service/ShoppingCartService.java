package com.redhat.coolstore.service;

import java.util.Hashtable;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.resteasy.annotations.Inject; // Add this import for Resteasy injection
import jakarta.ejb.Stateless; // Change @Stateful to @Stateless
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped; // Change @Inject to @ApplicationScoped for ShoppingCartOrderProcessor

import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.model.ShoppingCart;
import com.redhat.coolstore.model.ShoppingCartItem;

@Stateless
public class ShoppingCartService {

    @Inject
    Logger log;

    @Inject
    ProductService productServices;

    @Inject
    PromoService ps;

    @Inject
    @ApplicationScoped // Change to ApplicationScoped for ShoppingCartOrderProcessor
    ShoppingCartOrderProcessor shoppingCartOrderProcessor;

    private ShoppingCart cart  = new ShoppingCart();

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

                ps.applyCartItemPromotions(sc);

                for (ShoppingCartItem sci : sc.getShoppingCartItemList()) {

                    Product p = getProduct(sci.getProduct().getItemId());
                    //if product exist
                    if (p != null) {
                        sci.setProduct(p);
                        sci.setPrice(p.getPrice());
                    }

                    sci.setPromoSavings(0);
                }

                sc.setShippingTotal(calculateShipping(sc));

                if (sc.getCartItemTotal() >= 25) {
                    sc.setShippingTotal(sc.getShippingTotal()
                            + calculateShippingInsurance(sc));
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
            Product p = getProduct(sci.getProduct().getItemId());
            //if product exist
            if (p != null) {
                sci.setProduct(p);
                sci.setPrice(p.getPrice());
            }

            sci.setPromoSavings(0);
        }

    }

    public Product getProduct(String itemId) {
        return productServices.getProductByItemId(itemId);
    }

    private double calculateShipping(ShoppingCart sc) {
        // Implement shipping calculation logic here
        return 0;
    }

    private double calculateShippingInsurance(ShoppingCart sc) {
        // Implement shipping insurance calculation logic here
        return 0;
    }
}
