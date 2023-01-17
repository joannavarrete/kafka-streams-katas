package com.joannava.kafka.katas.serdes;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class JacksonSerdes<T> implements Serde<T> {

    private final Class<T> type;

    public JacksonSerdes(Class<T> type) {
        this.type = type;
    }

    @Override
    public Serializer<T> serializer() {
        return new JacksonSerializer<T>();
    }

    @Override
    public Deserializer<T> deserializer() {
        return new JacksonDeserializer<T>(type);
    }

}
