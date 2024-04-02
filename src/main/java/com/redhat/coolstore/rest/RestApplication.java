package com.redhat.coolstore.rest;

import jakarta.ws.rs.core.Application;
import io.quarkus.arc.processor.BeanArchive;

@BeanArchive(name = "services")
public class RestApplication extends Application {

}
