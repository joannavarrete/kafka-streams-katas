package com.joannava.kafka.katas.filters;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.TransactionSerdes;

public class SimpleFilterTopologyBuilder {

    private final StreamsBuilder builder;

    public SimpleFilterTopologyBuilder() {
        builder = new StreamsBuilder();
    }

    /**
     * Filtering with KStreams is very easy
     *
     * It is a stateless operation that does not need previous information to
     * execute
     * A filter operation receives a Predicate and returns a Stream
     * 
     * Here we rekey the stream and write it to a topic.
     * 
     */
    public Topology build() {
        KStream<Void, Transaction> stream = builder.stream("transactions",
                Consumed.with(Serdes.Void(), new TransactionSerdes()));

        stream
                .filter((key, transaction) -> transaction.getAccountId() == 443178)
                .selectKey((key, transaction) -> transaction.getAccountId())
                .to("simple_filter", Produced.with(Serdes.Integer(), new TransactionSerdes()));

        return builder.build();

    }

}
