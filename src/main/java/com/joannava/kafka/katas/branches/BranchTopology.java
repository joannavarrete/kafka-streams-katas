package com.joannava.kafka.katas.branches;

import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;

import com.joannava.kafka.katas.FromTransactionsStreamTopology;
import com.joannava.kafka.katas.model.Transaction;

public class BranchTopology extends FromTransactionsStreamTopology {

    public Topology build() {

        KStream<Integer, Transaction> stream = getTransactionsStream();
        
        stream.split()
                .branch((key, transaction) -> transaction.getTransactionCode().equals("buy"),
                        Branched.withConsumer(ks -> ks.to("buys")))
                .branch((key, transaction) -> transaction.getTransactionCode().equals("sell"),
                        Branched.withConsumer(ks -> ks.to("sells")));

        return builder.build();
    }
}
