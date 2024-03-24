// Update the InventoryEntity class to use the new jakarta.persistence package and annotations
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "INVENTORY")
public class InventoryEntity {

    @Id
    private String itemId;

    @Column
    private String location;

    @Column
    private int quantity;

    @Column
    private String link;

    public InventoryEntity() {

    }

    public InventoryEntity(String itemId, String location, int quantity, String link) {
        this.itemId = itemId;
        this.location = location;
        this.quantity = quantity;
        this.link = link;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "InventoryEntity [itemId=" + itemId + ", location=" + location + ", quantity=" + quantity + ", link=" + link + "]";
    }
}