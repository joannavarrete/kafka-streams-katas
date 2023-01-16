package com.joannava.kafka.katas.serdes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.joannava.kafka.katas.model.Transaction;

public class TransactionDeserializerTest {

    private String json = """
                    {
            	"amount": 4345,
            	"transaction_code": "sell",
            	"symbol": "ibm",
            	"price": "180.2130283892771558384993113577365875244140625",
            	"total": "783025.6083514092421182795078",
            	"date": "2013-05-23T00:00:00Z",
            	"accountId": "218657"
            }

                        """;

    private TransactionDeserializer transactionDeserializer = new TransactionDeserializer();

    @Test
    public void shouldDeserializeProperly() {
        Transaction transaction = transactionDeserializer.deserialize("hello world", json.getBytes());
        assertEquals(218657, transaction.getAccountId());
    }
}
