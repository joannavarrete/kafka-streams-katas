package com.joannava.kafka.katas.serdes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.joannava.kafka.katas.model.Account;
import com.joannava.kafka.katas.model.Customer;
import com.joannava.kafka.katas.model.Transaction;

public class JacksonDeserializerTest {

    private JacksonDeserializer<Transaction> deserializer = new JacksonDeserializer<>(Transaction.class);
    private JacksonDeserializer<Account> aDeserializer = new JacksonDeserializer<>(Account.class);
    private JacksonDeserializer<Customer> cDeserializer = new JacksonDeserializer<>(Customer.class);

    @Test
    public void shouldDeserializeTransactionProperly() {
        Transaction transaction = deserializer.deserialize("hello world", transactionJson.getBytes());
        assertEquals(218657, transaction.getAccountId());
    }

    @Test
    public void shouldDeserializeAccountProperly() {
        Account account = aDeserializer.deserialize("accountJson", accountJson.getBytes());
        assertEquals(745028, account.getId());
    }

    @Test
    public void shouldDeserializeCustomerProperly() {
        Customer customer = cDeserializer.deserialize("accountJson", customerJson.getBytes());
        assertEquals("fmiller", customer.getUsername());
    }

    private String customerJson = """
                    {
            	"_id": {
            		"$oid": "5ca4bbcea2dd94ee58162a68"
            	},
            	"username": "fmiller",
            	"name": "Elizabeth Ray",
            	"address": "9286 Bethany Glens\nVasqueztown, CO 22939",
            	"birthdate": {
            		"$date": "1977-03-02T02:20:31Z"
            	},
            	"email": "arroyocolton@gmail.com",
            	"active": true,
            	"accounts": [
            		371138,
            		324287,
            		276528,
            		332179,
            		422649,
            		387979
            	],
            	"tier_and_details": {
            		"0df078f33aa74a2e9696e0520c1a828a": {
            			"tier": "Bronze",
            			"id": "0df078f33aa74a2e9696e0520c1a828a",
            			"active": true,
            			"benefits": [
            				"sports tickets"
            			]
            		},
            		"699456451cc24f028d2aa99d7534c219": {
            			"tier": "Bronze",
            			"benefits": [
            				"24 hour dedicated line",
            				"concierge services"
            			],
            			"active": true,
            			"id": "699456451cc24f028d2aa99d7534c219"
            		}
            	}
            }

                        """;
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
