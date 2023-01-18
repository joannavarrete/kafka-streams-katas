package com.joannava.kafka.katas.producers;

import java.util.stream.StreamSupport;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.bson.Document;

import com.joannava.kafka.katas.mongo.SampleAnalyticsCollection;
import com.mongodb.client.FindIterable;

public class CostumersProducerApp {

    public static void main(String[] args) {

        SampleAnalyticsCollection accountsCollection = new SampleAnalyticsCollection("customers");
        FindIterable<Document> documents = accountsCollection.getAll();

        JsonProducer<String> producer = new JsonProducer<>(new StringSerializer());

        StreamSupport.stream(documents.spliterator(), true)
                .forEach(document -> producer
                        .send("customers", KeyValue.pair(
                                document.get("username", "unknown"), document.toJson())));

        producer.close();
    }
}
