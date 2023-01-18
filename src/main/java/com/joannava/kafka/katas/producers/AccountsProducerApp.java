package com.joannava.kafka.katas.producers;

import java.util.stream.StreamSupport;

import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.streams.KeyValue;
import org.bson.Document;

import com.joannava.kafka.katas.mongo.SampleAnalyticsCollection;
import com.mongodb.client.FindIterable;

public class AccountsProducerApp {
    public static void main(String[] args) {

        SampleAnalyticsCollection accountsCollection = new SampleAnalyticsCollection("accounts");
        FindIterable<Document> documents = accountsCollection.getAll();

        JsonProducer<Integer> producer = new JsonProducer<>(new IntegerSerializer());

        StreamSupport.stream(documents.spliterator(), true)
                .forEach(document -> producer
                        .send("accounts", KeyValue.pair(
                                document.get("account_id", 0), document.toJson())));

        producer.close();

    }
}
