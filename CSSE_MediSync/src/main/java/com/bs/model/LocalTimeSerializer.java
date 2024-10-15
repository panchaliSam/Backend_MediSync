package com.bs.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeSerializer implements JsonSerializer<LocalTime> {
    @Override
    public JsonElement serialize(LocalTime localTime, Type typeOfSrc, JsonSerializationContext context) {
        if (localTime == null) {
            return context.serialize(null); // Handle null values gracefully
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        return context.serialize(localTime.format(formatter)); // Serialize LocalTime to a formatted string
    }
}
