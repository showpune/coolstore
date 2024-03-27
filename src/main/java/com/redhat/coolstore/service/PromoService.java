// Write the updated file for Quarkus here
@QuarkusApplication(name = "coolstore-promo-service")
public class PromoService {

    private final Map<String, Promotion> promotionMap = new HashMap<>();

    @Inject
    public PromoService() {
        promotionMap.put("329299", new Promotion("329299", 0.25f));
    }

    @PostConstruct
    public void init() {
        // Add more promotions here
    }

    public void applyCartItemPromotions(ShoppingCart shoppingCart) {
        if (shoppingCart != null && shoppingCart.getShoppingCartItemList().size() > 0) {
            for (ShoppingCartItem sci : shoppingCart.getShoppingCartItemList()) {
                String productId = sci.getProduct().getItemId();
                Promotion promo = promotionMap.get(productId);
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

    public Set<Promotion> getPromotions() {
        return new HashSet<>(promotionMap.values());
    }

    public void setPromotions(Set<Promotion> promotions) {
        this.promotionMap.clear();
        this.promotionMap.putAll(promotions);
    }
}