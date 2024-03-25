package com.redhat.coolstore.utils;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Transformers {
    
    public JsonObject exampleMethod(String input) {
        JsonObject jsonObject = Json.createObjectBuilder()
            .add("key", "value")
            .build();
        
        return jsonObject;
    }
}