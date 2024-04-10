// Add import statements for jakarta.json
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import com.redhat.coolstore.model.CatalogItemEntity;
import com.redhat.coolstore.model.Order;
import com.redhat.coolstore.model.OrderItem;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.model.ProductImpl;
import com.redhat.coolstore.model.ShoppingCart;

// Update the import statements for the affected classes
import com.redhat.coolstore.utils.jakarta.json.JsonObjectBuilder;
import com.redhat.coolstore.utils.jakarta.json.JsonNumberBuilder;

// Update the affected methods
public static Product toProduct(CatalogItemEntity entity) {
    ProductImpl prod = new ProductImpl();
    prod.setItemId(entity.getItemId());
    prod.setName(entity.getName());
    prod.setDesc(entity.getDesc());
    prod.setPrice(entity.getPrice());
    if (entity.getInventory() != null) {
        prod.setLocation(entity.getInventory().getLocation());
        prod.setLink(entity.getInventory().getLink());
        prod.setQuantity(entity.getInventory().getQuantity());
    } else {
        log.warning("Inventory for " + entity.getName() + "[" + entity.getItemId()+ "] unknown and missing");
    }
    return prod;
}

// Update the affected methods
public static String shoppingCartToJson(ShoppingCart cart) {
    JsonArrayBuilder cartItems = Json.createArrayBuilder();
    cart.getShoppingCartItemList().forEach(item -> {
        cartItems.add(Json.createObjectBuilder()
            .add("productSku",item.getProduct().getItemId())
            .add("quantity",item.getQuantity())
        );
    });

    int randomNameAndEmailIndex = ThreadLocalRandom.current().nextInt(RANDOM_NAMES.length);

    JsonObject jsonObject = Json.createObjectBuilder()
        .add("orderValue", Double.valueOf(cart.getCartTotal()))
        .add("customerName",RANDOM_NAMES[randomNameAndEmailIndex])
        .add("customerEmail",RANDOM_EMAILS[randomNameAndEmailIndex])
        .add("retailPrice", cart.getShoppingCartItemList().stream().mapToDouble(i -> i.getQuantity()*i.getPrice()).sum())
        .add("discount", Double.valueOf(cart.getCartItemPromoSavings()))
        .add("shippingFee", Double.valueOf(cart.getShippingTotal()))
        .add("shippingDiscount", Double.valueOf(cart.getShippingPromoSavings()))
        .add("items",cartItems) 
        .build();
    StringWriter w = new StringWriter();
    try (JsonWriter writer = Json.createWriter(w)) {
        writer.write(jsonObject);
    }
    return w.toString();
}

// Update the affected methods
public static Order jsonToOrder(String json) {
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonObject rootObject = jsonReader.readObject();
    Order order = new Order();
    order.setCustomerName(rootObject.getString("customerName"));
    order.setCustomerEmail(rootObject.getString("customerEmail"));
    order.setOrderValue(rootObject.getJsonNumber("orderValue").doubleValue());
    order.setRetailPrice(rootObject.getJsonNumber("retailPrice").doubleValue());
    order.setDiscount(rootObject.getJsonNumber("discount").doubleValue());
    order.setShippingFee(rootObject.getJsonNumber("shippingFee").doubleValue());
    order.setShippingDiscount(rootObject.getJsonNumber("shippingDiscount").doubleValue());
    JsonArray jsonItems = rootObject.getJsonArray("items");
    List<OrderItem> items = new ArrayList<OrderItem>(jsonItems.size());
    for (JsonObject jsonItem : jsonItems.getValuesAs(JsonObject.class)) {
        OrderItem oi = new OrderItem();
        oi.setProductId(jsonItem.getString("productSku"));
        oi.setQuantity(jsonItem.getInt("quantity"));
        items.add(oi);
    }
    order.setItemList(items); 
    return order;
}
