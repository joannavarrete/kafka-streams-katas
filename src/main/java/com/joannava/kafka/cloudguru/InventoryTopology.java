package com.joannava.kafka.cloudguru;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.math.BigDecimal;

public class InventoryTopology {
    
   public Topology build() {
       StreamsBuilder builder = new StreamsBuilder();
        KStream<String,String> stream =  builder
           .stream("inventory_purchases",
                   Consumed.with(Serdes.String(), Serdes.String()));
       
        stream.map((key, value) -> KeyValue.pair(key, Integer.valueOf(value)))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Integer()))
                .reduce((a, b) -> a + b)
                .toStream()
                .foreach((key, value) -> System.out.println("key=" + key + ", value=" + value));

       return builder.build(); 

   } 
}
