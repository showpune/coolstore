package com.redhat.coolstore.service;

import com.redhat.coolstore.model.ShoppingCart;

public interface ShippingServiceRemote {
    void calculateShipping(ShoppingCart sc);
}
