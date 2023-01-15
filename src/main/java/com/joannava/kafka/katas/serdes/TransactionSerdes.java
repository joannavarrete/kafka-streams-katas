package com.joannava.kafka.katas.serdes;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.joannava.kafka.katas.model.Transaction;

public class TransactionSerdes implements Serde<Transaction>{

    @Override
    public Serializer<Transaction> serializer() {
    return new TransactionSerializer();
    }

    @Override
    public Deserializer<Transaction> deserializer() {
    return new TransactionDeserializer();
    }
    
}
