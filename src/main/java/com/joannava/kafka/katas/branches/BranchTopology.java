package com.joannava.kafka.katas.branches;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;

import com.joannava.kafka.katas.SampleAnalyticsStreamTopology;
import com.joannava.kafka.katas.model.Transaction;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

import java.math.BigDecimal;

public class BranchTopology extends SampleAnalyticsStreamTopology {

    public Topology build() {

        KStream<Integer, Transaction> stream = getTransactionsStream();
                
        stream.filter((key, value) -> value.getSymbol().equals("adbe"))
                .map((key, value) -> KeyValue.pair(value.getAccountId(),value.getTotal().toString()))
                .to("adbe", Produced.with(Serdes.Integer(), Serdes.String()));
        
        

        
//        stream.split()
//                .branch((key, transaction) -> transaction.getTransactionCode().equals("buy"),
//                        Branched.withConsumer(ks -> ks.to("buys")))
//                .branch((key, transaction) -> transaction.getTransactionCode().equals("sell"),
//                        Branched.withConsumer(ks -> ks.to("sells")));

        return builder.build();
    }
}
