package com.joannava.kafka.katas.aggregations;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.TransactionSerdes;

public class CountTopology {
    private final StreamsBuilder builder;

    public CountTopology() {
        builder = new StreamsBuilder();
    }

    public Topology build() {
        KStream<Integer, Transaction> stream = builder.stream("transactions",
                Consumed.with(Serdes.Integer(), new TransactionSerdes()));

        stream.groupByKey()
                .count()
                .toStream()
                .to("count_by_accountId", Produced.with(Serdes.Integer(), Serdes.Long()));

        return builder.build();
    }

}
