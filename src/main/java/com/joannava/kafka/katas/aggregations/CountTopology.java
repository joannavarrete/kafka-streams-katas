package com.joannava.kafka.katas.aggregations;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.FromTransactionsStreamTopology;
import com.joannava.kafka.katas.model.Transaction;

public class CountTopology extends FromTransactionsStreamTopology {

    public Topology build() {

        KStream<Integer, Transaction> stream = getTransactionsStream();

        stream.groupByKey()
                .count()
                .toStream()
                .to("count_by_accountId", Produced.with(Serdes.Integer(), Serdes.Long()));

        return builder.build();
    }

}
