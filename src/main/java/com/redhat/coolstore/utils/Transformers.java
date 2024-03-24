import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RandomService;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import quarkus.test.junit.QuarkusTest;

@ExtendWith(QuarkusTest.class)
public class Transformers {

    private static final Logger log = Mockito.mock(Logger.class);

    @QuarkusMock
    private ShoppingCart cart;

    @Test
    public void testShoppingCartToJson() {
        String json = "{\"orderValue\":12345.67,\"customerName\":\"Sven Karlsson\",\"customerEmail\":\"sven@gmail.com\",\"retailPrice\":12345.67,\"discount\":0.00,\"shippingFee\":0.00,\"shippingDiscount\":0.00,\"items\":[{\"productSku\":\"1001\",\"quantity\":1,\"price\":100.00},{\"productSku\":\"1002\",\"quantity\":2,\"price\":200.00}],\"shippingTotal\":10.00,\"shippingPromoSavings\":5.00}";
        Order expectedOrder = new Order();
        expectedOrder.setCustomerName("Sven Karlsson");
        expectedOrder.setCustomerEmail("sven@gmail.com");
        expectedOrder.setOrderValue(12345.67);
        expectedOrder.setRetailPrice(12345.67);
        expectedOrder.setDiscount(0.00);
        expectedOrder.setShippingFee(0.00);
        expectedOrder.setShippingDiscount(0.00);
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem().setProductId("1001").setQuantity(1).setPrice(100.00));
        items.add(new OrderItem().setProductId("1002").setQuantity(2).setPrice(200.00));
        expectedOrder.setItemList(items);

        JsonWriter writer = Json.createWriter(System.out);
        writer.write(Transformers.shoppingCartToJson(cart));
        writer.close();

        Mockito.verify(log, Mockito.times(1)).warning(Mockito.anyString());
        Mockito.verify(cart, Mockito.times(1)).getShoppingCartItemList();
    }
}