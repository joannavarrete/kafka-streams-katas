package com.joannava.kafka.katas;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import com.joannava.kafka.katas.model.Account;
import com.joannava.kafka.katas.model.Customer;
import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonSerdes;

public abstract class SampleAnalyticsStreamTopology {

    protected final StreamsBuilder builder;

    public SampleAnalyticsStreamTopology() {
        builder = new StreamsBuilder();
    }

    protected KStream<Integer, Transaction> getTransactionsStream() {
        return builder.stream("transactions",
                Consumed.with(Serdes.Integer(),
                        new JacksonSerdes<>(Transaction.class)));
    }    

    protected KStream<Integer, Account> getAccountsStream() {
        return builder.stream("account",
                Consumed.with(Serdes.Integer(),
                        new JacksonSerdes<>(Account.class)));
    }    

    protected KStream<String, Customer> getCustomersStream() {
        return builder.stream("customers",
                Consumed.with(Serdes.String(),
                        new JacksonSerdes<>(Customer.class)));
    }    

    public abstract Topology build();
}
