package com.joannava.kafka.katas.serdes;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joannava.kafka.katas.model.Transaction;

public class TransactionSerializer implements Serializer<Transaction>{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Transaction data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
