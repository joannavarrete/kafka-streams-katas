package com.joannava.kafka.katas.branches;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonSerdes;

public class BranchTopologyTest {
    private final BranchTopology topology = new BranchTopology();

    private TopologyTestDriver tp;

    private JacksonSerdes<Transaction> jacksonSerdes;
    private TestInputTopic<Integer, Transaction> inputTopic;
    private TestOutputTopic<Integer, Transaction> buysTopic;
    private TestOutputTopic<Integer, Transaction> sellsTopic;

    @BeforeEach
    public void beforeEach() {
        tp = new TopologyTestDriver(topology.build());
        jacksonSerdes = new JacksonSerdes<>(Transaction.class);

        // setup test topics
        inputTopic = tp.createInputTopic("transactions", new IntegerSerializer(), jacksonSerdes.serializer());
        buysTopic = tp.createOutputTopic("buys", new IntegerDeserializer(), jacksonSerdes.deserializer());
        sellsTopic = tp.createOutputTopic("sells", new IntegerDeserializer(), jacksonSerdes.deserializer());
    }

    @AfterEach
    public void tearDown() {
        tp.close();
        jacksonSerdes.close();
    }

    @Test
    public void whenBuyShouldBePlacedInItsTopic() {
        inputTopic.pipeInput(1, Transaction.builder().transactionCode("buy").build());
        assertEquals(true, sellsTopic.isEmpty());
        assertEquals(false, buysTopic.isEmpty());
    }

    @Test
    public void whenSellShouldBePlacedInItsTopic() {
        inputTopic.pipeInput(1, Transaction.builder().transactionCode("sell").build());
        assertEquals(false, sellsTopic.isEmpty());
        assertEquals(true, buysTopic.isEmpty());

    }

}
