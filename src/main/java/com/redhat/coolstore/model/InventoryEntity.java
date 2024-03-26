// Update the InventoryEntity class to use the new jakarta.persistence and jakarta.xml.bind.annotation packages
package com.redhat.coolstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.UUID;

@Entity
@XmlRootElement(namespace = "http://www.example.com")
@Table(name = "INVENTORY", uniqueConstraints = @UniqueConstraint(columnNames = "itemId"))
public class InventoryEntity implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId", nullable = false, updatable = false, columnDefinition = "VARCHAR(255)")
    private String itemId;

    @Column(name = "location", nullable = false, updatable = false)
    private String location;

    @Column(name = "quantity", nullable = false, updatable = false)
    private int quantity;

    @Column(name = "link", nullable = false, updatable = false)
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