package com.joannava.kafka.katas;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonSerdes;

public class FromTransactionsStreamTopology {

    protected final StreamsBuilder builder;

    public FromTransactionsStreamTopology() {
        builder = new StreamsBuilder();
    }

    protected KStream<Integer, Transaction> getTransactionsStream() {
        return builder.stream("transactions",
                Consumed.with(Serdes.Integer(),
                        new JacksonSerdes<Transaction>(Transaction.class)));
    }    
}
