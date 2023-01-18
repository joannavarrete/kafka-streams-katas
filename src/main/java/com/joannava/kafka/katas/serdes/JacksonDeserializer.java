package com.joannava.kafka.katas.serdes;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper mapper;

    private final Class<T> type;

    public JacksonDeserializer(Class<T> type) {
        this.type = type;
        mapper = new ObjectMapper();
        mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
