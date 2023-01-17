package com.joannava.kafka.katas.aggregations;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.TransactionSerdes;

public class SumSellsTopology {

    private final StreamsBuilder builder;

    public SumSellsTopology() {
        builder = new StreamsBuilder();
    }

    public Topology build() {
        KStream<Integer, Transaction> stream = builder.stream("transactions",
                Consumed.with(Serdes.Integer(), new TransactionSerdes()));

        stream.filter((key, transaction) -> transaction.getTransactionCode().equals("sell"))
                .map((key, transaction) -> KeyValue.pair(key, transaction.getTotal()))
                .groupByKey(Grouped.with(Serdes.Integer(), Serdes.Float()))
                .reduce(Float::sum)
                .toStream().to("total_sold_by_account", Produced.with(Serdes.Integer(), Serdes.Float()));

        return builder.build();
    }

}
