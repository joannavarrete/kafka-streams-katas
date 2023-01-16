
package com.joannava.kafka.katas.mongo;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoCollection;

import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Mocked;
import mockit.Tested;

public class TransactionCollectionTest {

    @Mocked
    private MongoCollection<Document> mongoCollection;

    @Tested
    private TransactionCollection collection = new TransactionCollection();

    private static final String dbName = "sample_analytics";
    private static final String collectionName = "transactions";

    @Test
    public void whenInstantiatedThenMongoCollectionHasDbNameSampleAnalytics() {
        assertEquals(dbName, collection.getMongoCollection().getNamespace().getDatabaseName());
    }

    @Test
    public void whenInstantiatedThenMongoCollectionHasCollectionNameTransactions() {
        assertEquals(collectionName, collection.getMongoCollection().getNamespace().getCollectionName());
    }

    @Test
    public void whenFindReturnTheCollection() {
        new Expectations(collection) {
            {
                collection.getMongoCollection();
                result = mongoCollection;
            }
        };

        collection.getAll();

        new FullVerifications() {
            {
                mongoCollection.find();
                times = 1;
            }
        };
    }
}
