package com.joannava.kafka.katas.aggregations;

import static org.apache.kafka.streams.kstream.Grouped.with;

import java.math.BigDecimal;
import java.math.MathContext;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import com.joannava.kafka.katas.SampleAnalyticsStreamTopology;
import com.joannava.kafka.katas.model.CountAndSum;
import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonSerdes;

public class SymbolAverageTopology extends SampleAnalyticsStreamTopology {

    @Override
    public Topology build() {
        KStream<Integer, Transaction> stream = getTransactionsStream();

        stream
                .map((key, value) -> KeyValue.pair(value.getSymbol(), value.getPrice()))
                .groupByKey(with(Serdes.String(), new JacksonSerdes<>(BigDecimal.class)))
                .aggregate(() -> CountAndSum.builder().count(0).sum(new BigDecimal("0")).build(),
                        (symbol, price, countAndSum) -> {
                            return CountAndSum.builder()
                                    .count(countAndSum.getCount() + 1)
                                    .sum(countAndSum.getSum().add(price))
                                    .build();
                        },
                        Materialized.with(Serdes.String(), new JacksonSerdes<>(CountAndSum.class)))
                .mapValues(countAndSum -> countAndSum.getSum()
                        .divide(BigDecimal.valueOf(countAndSum.getCount()), MathContext.DECIMAL128))
                .toStream()
                .to("symbol_average", Produced.with(Serdes.String(), new JacksonSerdes<>(BigDecimal.class)));

        return builder.build();
    }

}
