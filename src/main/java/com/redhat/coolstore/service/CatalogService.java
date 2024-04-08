package com.redhat.coolstore.service;

import java.util.List;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Qualifier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.redhat.coolstore.model.*;

@ApplicationScoped
public class CatalogService {

    @Inject
    Logger log;

    @PersistenceContext(name = "entityManager")
    @Named("entityManager")
    @ExtendedContext
    EntityManager em;

    public CatalogService() {
    }

    public List<CatalogItemEntity> getCatalogItems() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CatalogItemEntity> criteria = cb.createQuery(CatalogItemEntity.class);
        Root<CatalogItemEntity> member = criteria.from(CatalogItemEntity.class);
        criteria.select(member);
        return em.createQuery(criteria).getResultList();
    }

    public CatalogItemEntity getCatalogItemById(String itemId) {
        return em.find(CatalogItemEntity.class, itemId);
    }

    public void updateInventoryItems(String itemId, int deducts) {
        InventoryEntity inventoryEntity = getCatalogItemById(itemId).getInventory();
        int currentQuantity = inventoryEntity.getQuantity();
        inventoryEntity.setQuantity(currentQuantity-deducts);
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExtendedContext {
    }

}
