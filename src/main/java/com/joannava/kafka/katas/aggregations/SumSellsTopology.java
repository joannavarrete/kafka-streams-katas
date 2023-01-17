package com.joannava.kafka.katas.aggregations;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.FromTransactionsStreamTopology;
import com.joannava.kafka.katas.model.Transaction;

public class SumSellsTopology extends FromTransactionsStreamTopology{

    public Topology build() {

        KStream<Integer, Transaction> stream = getTransactionsStream();
        
        stream.filter((key, transaction) -> transaction.getTransactionCode().equals("sell"))
                .map((key, transaction) -> KeyValue.pair(key, transaction.getTotal()))
                .groupByKey(Grouped.with(Serdes.Integer(), Serdes.Float()))
                .reduce(Float::sum)
                .toStream().to("total_sold_by_account", Produced.with(Serdes.Integer(), Serdes.Float()));

        return builder.build();
    }

}