package com.joannava.kafka.katas.aggregations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.apache.kafka.common.serialization.FloatDeserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.TransactionSerializer;

public class SumSellsTopologyTest {
   private final SumSellsTopology topology = new SumSellsTopology();
   
    private TopologyTestDriver tp;

    private TestInputTopic<Integer, Transaction> inputTopic;
    private TestOutputTopic<Integer, Float> outputTopic;

    @BeforeEach
    public void beforeEach() {
        tp = new TopologyTestDriver(topology.build());
        // setup test topics
        inputTopic = tp.createInputTopic("transactions", new IntegerSerializer(), new TransactionSerializer());
        outputTopic = tp.createOutputTopic("total_sold_by_account", new IntegerDeserializer(), new FloatDeserializer());
    }

    @AfterEach
    public void tearDown() {
        tp.close();
    }

    @Test
    public void shouldFilterOutTheBuys(){
        inputTopic.pipeInput(3334,Transaction.builder().transactionCode("buy").build());
        assertEquals(true, outputTopic.isEmpty());
        inputTopic.pipeInput(3334,Transaction.builder().transactionCode("sell").build());
        assertEquals(false, outputTopic.isEmpty());
    }

    @Test
    public void shouldSumSellsByAccount(){
        inputTopic.pipeInput(3334,Transaction.builder().transactionCode("sell").total(50).build());
        inputTopic.pipeInput(3334,Transaction.builder().transactionCode("sell").total(50).build());
        inputTopic.pipeInput(3334,Transaction.builder().transactionCode("sell").total(50).build());
        inputTopic.pipeInput(3334,Transaction.builder().transactionCode("buy").total(50).build());
        inputTopic.pipeInput(444,Transaction.builder().transactionCode("sell").total(50).build());

        Map<Integer, Float> keyValues = outputTopic.readKeyValuesToMap();
        assertEquals(150, keyValues.get(3334));
        assertEquals(50, keyValues.get(444));
    }
}
