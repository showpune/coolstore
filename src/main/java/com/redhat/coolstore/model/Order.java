// Update the `Order` class to use `jakarta.persistence`
import io.quarkus.hibernate.orm.jpa.PersistenceConfiguration;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    private long orderId;

    private String customerName;

    private String customerEmail;

    private double orderValue;

    private double retailPrice;

    private double discount;

    private double shippingFee;

    private double shippingDiscount;

    @Column(name="TOTAL_PRICE")

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="ORDER_ID")
    private List<OrderItem> itemList = new ArrayList<>();

    // Add @Transient annotation to remove the `discount` field from the `toArray()` method
    @Transient
    private double discountField;

    // Add @Max and @Min constraints to the `orderValue` and `retailPrice` fields
    @Max(Double.MAX_VALUE)
    @Min(Double.MIN_VALUE)
    @Column(name="ORDER_VALUE")
    private double orderValue;

    @Max(Double.MAX_VALUE)
    @Min(Double.MIN_VALUE)
    @Column(name="RETAIL_PRICE")
    private double retailPrice;

    // Update the `OrderItem` class to use `jakarta.persistence`
    import jakarta.persistence.ManyToOne;
    import jakarta.persistence.OneToOne;

    public class OrderItem {

        @Id
        @GeneratedValue
        private long orderItemId;

        private String itemName;

        private double itemPrice;

        @ManyToOne
        private Order order;

        public OrderItem() {}

        public OrderItem(String itemName, double itemPrice, Order order) {
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.order = order;
        }

        public long getOrderItemId() {
            return orderItemId;
        }

        public void setOrderItemId(long orderItemId) {
            this.orderItemId = orderItemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public double getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(double itemPrice) {
            this.itemPrice = itemPrice;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

    }

    // Update the `pom.xml` file to include the `jakarta.persistence` dependency
    <dependency>
        <groupId> jakarta.persistence </groupId>
        <artifactId> jakarta.persistence-api </artifactId>
        <version> 2.2 </version>
    </dependency>

}