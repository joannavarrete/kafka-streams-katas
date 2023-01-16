package com.joannava.kafka.katas.producers;

import java.io.IOException;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.joannava.kafka.katas.mongo.TransactionCollection;
import com.joannava.kafka.katas.utils.JsonUtils;
import com.mongodb.client.FindIterable;

public class ProducerApp {

    public static void main(String[] args) throws IOException {

        TransactionCollection transactionCollection = new TransactionCollection();
        FindIterable<Document> documents = transactionCollection.getAll();

        TransactionProducer producer = new TransactionProducer();

        StreamSupport.stream(documents.spliterator(), true)
                .flatMap(doc -> JsonUtils.extractTransactionsFromParent(doc.toJson()).stream())
                .forEach(transaction -> producer.send(transaction));

        producer.close();

    }


}
