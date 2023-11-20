package com.joannava.kafka.katas.producers;

import java.io.IOException;
import java.util.stream.StreamSupport;

import com.sun.source.tree.TryTree;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.bson.Document;

import com.joannava.kafka.katas.mongo.SampleAnalyticsCollection;
import com.joannava.kafka.katas.utils.JsonUtils;
import com.mongodb.client.FindIterable;



public class TransactionProducerApp {

    public static void main(String[] args) throws IOException {

        SampleAnalyticsCollection transactionCollection = new SampleAnalyticsCollection("transactions");
        FindIterable<Document> documents = transactionCollection.getAll();

        JsonProducer<Integer> producer = new JsonProducer<>(new IntegerSerializer());

        StreamSupport.stream(documents.spliterator(), true)
                .flatMap(doc -> JsonUtils.extractTransactionsFromParent(doc.toJson()).stream())
                .forEach(keyValue -> {
                            try {
                                Thread.sleep(100);
                                producer.send("transactions", keyValue);
//                                System.out.println(keyValue);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
//        .forEach(keyValue -> System.out.println(keyValue));
        producer.close();

    }


}
