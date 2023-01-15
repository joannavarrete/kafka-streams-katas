package com.jonnava.kafka.katas.mongo;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TransactionCollection {

    private final MongoCollection<Document> collection;

    public TransactionCollection() {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("sample_analytics");
        collection = database.getCollection("transactions");
    }

    protected MongoCollection<Document> getMongoCollection() {
        return collection;
    }

    public FindIterable<Document> getAll() {
        return getMongoCollection().find();
    }

    
}
