package com.redhat.coolstore.utils;

import jakarta.json.Json;
import jakarta.json.JsonGenerator;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonGeneratorFactory;

public class Transformers {

    public static JsonObject convertToJsonObject(String jsonString) {
        return Json.createReader(jsonString).readObject();
    }

    public static String convertToJsonString(JsonObject jsonObject) {
        try (JsonGenerator generator = Json.createGenerator(System.out)) {
            generator.writeStartObject();
            jsonObject.entrySet().forEach(entry -> {
                generator.write(entry.getKey(), entry.getValue().toString());
            });
            generator.writeEnd();
        }
        return null;
    }

}