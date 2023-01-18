package com.joannava.kafka.katas.mongo;

import static com.joannava.kafka.katas.utils.PropertiesUtils.getProperties;

import java.util.Properties;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
 
public class SampleAnalyticsCollection {

    private final MongoCollection<Document> collection;

    public SampleAnalyticsCollection(String collectionName) {
        Properties appProperties = getProperties();
     
        MongoClient mongoClient = MongoClients.create(appProperties.getProperty("mongo.connection.string"));
        MongoDatabase database = mongoClient.getDatabase("sample_analytics");
        collection = database.getCollection(collectionName);
    }

    protected MongoCollection<Document> getMongoCollection() {
        return collection;
    }

    public FindIterable<Document> getAll() {
        return getMongoCollection().find();
    }

    
}
