package com.joannava.kafka.katas.aggregations;

import java.math.BigDecimal;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.FromTransactionsStreamTopology;
import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonSerdes;

public class SumSellsTopology extends FromTransactionsStreamTopology {

    public Topology build() {

        KStream<Integer, Transaction> stream = getTransactionsStream();

        stream.filter((key, transaction) -> transaction.getTransactionCode().equals("sell"))
                .map((key, transaction) -> KeyValue.pair(key, transaction.getTotal()))
                .groupByKey(Grouped.with(Serdes.Integer(), new JacksonSerdes<>(BigDecimal.class)))
                .reduce((a, b) -> a.add(b))
                .toStream()
                .to("total_sold_by_account", Produced.with(Serdes.Integer(), new JacksonSerdes<>(BigDecimal.class)));

        return builder.build();
    }

}
