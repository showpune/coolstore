// Update the RestApplication class to use jakarta.ws.rs and remove the JAX-RS activation
package com.redhat.coolstore.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/services")
public class RestApplication extends Application {

}