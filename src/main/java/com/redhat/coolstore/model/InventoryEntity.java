package com.redhat.coolstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class InventoryEntity extends PanacheEntityBase {

    @Id
    public Long id;
    public String name;
    public int stock;

    // Assuming a simple constructor for demonstration
    public InventoryEntity() {}

    // Example getters and setters or other methods

}