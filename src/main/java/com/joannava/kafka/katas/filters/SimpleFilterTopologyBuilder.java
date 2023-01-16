package com.joannava.kafka.katas.filters;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.TransactionSerdes;

public class SimpleFilterTopologyBuilder {

    private final StreamsBuilder builder;

    public SimpleFilterTopologyBuilder() {
        builder = new StreamsBuilder();
    }

    public Topology build() {
        KStream<Void, Transaction> stream = builder.stream("transactions",
                Consumed.with(Serdes.Void(), new TransactionSerdes()));

        KStream<Integer, Transaction> filtered = stream
                .filter((key, transaction) -> transaction.getAccountId() == 443178)
                .selectKey((key, transaction) -> transaction.getAccountId());
                // .groupByKey()
                // .count();

        filtered.to("filter_try", Produced.with(Serdes.Integer(), new TransactionSerdes()));

        return builder.build();

    }

}
