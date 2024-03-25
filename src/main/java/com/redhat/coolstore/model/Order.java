package com.redhat.coolstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Order extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Assuming other fields, getters, and setters here
    // ...

    // Example method leveraging Panache functionality
    public static Order findById(Long id) {
        return find("id", id).firstResult();
    }
}