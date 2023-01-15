package com.joannava.kafka.katas.serdes;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joannava.kafka.katas.model.Transaction;

public class TransactionDeserializer implements Deserializer<Transaction> {

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Transaction deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, Transaction.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
