package com.joannava.kafka.katas.aggregations;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.FromTransactionsStreamTopology;
import com.joannava.kafka.katas.model.Transaction;

public class NetProfitTopology extends FromTransactionsStreamTopology {


    public Topology build() {

        KStream<Integer, Transaction> stream = getTransactionsStream();
        
        stream
                .map((key, transaction) -> KeyValue.pair(key,
                        transaction.getTransactionCode().equals("sell") ? transaction.getTotal()
                                : -transaction.getTotal()))
                .groupByKey(Grouped.with(Serdes.Integer(), Serdes.Float()))
                .reduce(Float::sum)
                .toStream().to("net_profit_by_account", Produced.with(Serdes.Integer(), Serdes.Float()));

        return builder.build();
    }

}
