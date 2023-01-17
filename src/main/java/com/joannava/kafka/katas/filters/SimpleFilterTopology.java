package com.joannava.kafka.katas.filters;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.FromTransactionsStreamTopology;
import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonSerdes;

public class SimpleFilterTopology extends FromTransactionsStreamTopology {

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

        KStream<Integer, Transaction> stream = getTransactionsStream();
        stream
                .filter((key, transaction) -> transaction.getAccountId() == 443178)
                .to("simple_filter",
                        Produced.with(Serdes.Integer(), new JacksonSerdes<Transaction>(Transaction.class)));

        return builder.build();

    }

}
