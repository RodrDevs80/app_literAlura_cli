package com.javaalura.literAlura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ConvierteDatos implements IConvierteDatos {
    private final ObjectMapper objectMapper= new ObjectMapper();

    @Override
    public <T> T convertdata(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json.toString(), clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
