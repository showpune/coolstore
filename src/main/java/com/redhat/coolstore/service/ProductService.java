package com.redhat.coolstore.service;

import com.redhat.coolstore.model.CatalogItemEntity;
import com.redhat.coolstore.model.Product;
import com.redhat.coolstore.utils.Transformers;

import jakarta.inject.Inject; // Correct for CDI in Quarkus
import javax.enterprise.context.ApplicationScoped; // Correct for CDI in Quarkus
import java.util.List;
import java.util.stream.Collectors;

import static com.redhat.coolstore.utils.Transformers.toProduct;

@ApplicationScoped // Correct for CDI Bean in Quarkus
public class ProductService {

    @Inject // Correct for Quarkus
    CatalogService cm;

    public ProductService() {
    }

    public List<Product> getProducts() {
        return cm.getCatalogItems().stream().map(Transformers::toProduct).collect(Collectors.toList());
    }

    public Product getProductByItemId(String itemId) {
        CatalogItemEntity entity = cm.getCatalogItemById(itemId);
        if (entity == null)
            return null;

        return Transformers.toProduct(entity);
    }
}