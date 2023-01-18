package com.joannava.kafka.katas.serdes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.joannava.kafka.katas.model.Account;
import com.joannava.kafka.katas.model.Transaction;

public class JacksonDeserializerTest {

    private JacksonDeserializer<Transaction> deserializer = new JacksonDeserializer<>(Transaction.class);
    private JacksonDeserializer<Account> aDeserializer = new JacksonDeserializer<>(Account.class);

    @Test
    public void shouldDeserializeTransactionProperly() {
        Transaction transaction = deserializer.deserialize("hello world", transactionJson.getBytes());
        assertEquals(218657, transaction.getAccountId());
    }

    @Test
    public void shouldDeserializeAccountProperly(){
        Account account = aDeserializer.deserialize("accountJson", accountJson.getBytes());
        assertEquals(745028, account.getId());
    }

    private String accountJson = """
               {
            "_id": {
            	"$oid": "5ca4bbc7a2dd94ee5816258f"
            },
            "account_id": 745028,
            "limit": 10000,
            "products": [
            	"CurrencyService",
            	"InvestmentStock"
            ]}

               """;
    private String transactionJson = """
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
}
