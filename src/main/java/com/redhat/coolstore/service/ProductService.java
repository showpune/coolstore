package com.redhat.coolstore.service;

import com.redhat.coolstore.model.Product;
import io.quarkus.hibernate.orm.PanacheEntity;
import io.quarkus.hibernate.orm.PanacheEntityBase;
import io.quarkus.hibernate.orm.PanacheRepository;
import io.quarkus.hibernate.orm.PanacheRepositoryBase;
import io.quarkus.logging.Log;
import io.quarkus.logging.Loggers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ProductService {

    private static final Logger LOGGER = Loggers.getLogger(ProductService.class);

    @Inject
    PanacheEntityBase<Product, Long> productBase;

    public List<Product> findAll() {
        return productBase.listAll();
    }

    public Product findById(Long id) {
        return productBase.findById(id);
    }

    public Product create(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        productBase.persist(product);
        return product;
    }

    public Product update(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        return productBase.update(product);
    }

    public void delete(Long id) {
        Product product = findById(id);
        if (product != null) {
            productBase.delete(product);
        }
    }

    @Override
    public String toString() {
        return "ProductService{" +
                "productBase=" + productBase +
                '}';
    }
}