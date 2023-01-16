package com.joannava.kafka.katas.filters;

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
import com.joannava.kafka.katas.serdes.TransactionDeserializer;
import com.joannava.kafka.katas.serdes.TransactionSerializer;

public class SimpleFilterTopologyTest {

    private final SimpleFilterTopology topology = new SimpleFilterTopology();

    private TopologyTestDriver tp;

    private TestInputTopic<Integer, Transaction> inputTopic;
    private TestOutputTopic<Integer, Transaction> outputTopic;

    @BeforeEach
    public void beforeEach() {
        tp = new TopologyTestDriver(topology.build());
        // setup test topics
        inputTopic = tp.createInputTopic("transactions", new IntegerSerializer(), new TransactionSerializer());
        outputTopic = tp.createOutputTopic("simple_filter", new IntegerDeserializer(), new TransactionDeserializer());

    }

    @AfterEach
    public void tearDown() {
        tp.close();
    }

    @Test
    public void whenSendingATransactionWithDifferentAccountIdShouldItShouldBeFilteredOut() {
        inputTopic.pipeInput(3334,Transaction.builder().accountId(3334).build());
        assertEquals(true, outputTopic.isEmpty());
    }

    @Test
    public void whenSendingATransactionWithRightAccountItShouldBeKept() {
        inputTopic.pipeInput(443178, Transaction.builder().accountId(443178).build());
        assertEquals(false, outputTopic.isEmpty());
    }
}
