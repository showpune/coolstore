// Update the OrderItem class to use jakarta.persistence
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem implements Serializable {
    // ...
}