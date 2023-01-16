package com.joannava.kafka.katas.branches;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.TransactionSerdes;

public class BranchTopology {

    private final StreamsBuilder builder;

    public BranchTopology() {
        builder = new StreamsBuilder();
    }

    public Topology build() {
        KStream<Void, Transaction> stream = builder.stream("transactions",
                Consumed.with(Serdes.Void(), new TransactionSerdes()));

        stream.split()
                .branch((key, transaction) -> transaction.getTransactionCode().equals("buy"),
                        Branched.withConsumer(ks -> ks.to("buys")))
                .branch((key, transaction) -> transaction.getTransactionCode().equals("sell"),
                        Branched.withConsumer(ks -> ks.to("sells")));

        return builder.build();
    }
}
