package com.joannava.kafka.katas.aggregations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.TransactionSerializer;

public class CountTopologyTest {
    private CountTopology topology = new CountTopology();
    private TopologyTestDriver tp;

    private TestInputTopic<Integer, Transaction> inputTopic;
    private TestOutputTopic<Integer, Long> countTopic;

    @BeforeEach
    public void beforeEach() {
        tp = new TopologyTestDriver(topology.build());
        // setup test topics
        inputTopic = tp.createInputTopic("transactions", new IntegerSerializer(), new TransactionSerializer());
        countTopic = tp.createOutputTopic("count_by_accountId", new IntegerDeserializer(), new LongDeserializer());
    }

    @AfterEach
    public void tearDown() {
        tp.close();
    }

    @Test
    public void shouldCountNumberOfTransactionsForAccount() {
        inputTopic.pipeInput(1,Transaction.builder().accountId(1).build());
        inputTopic.pipeInput(1,Transaction.builder().accountId(1).build());
        inputTopic.pipeInput(1,Transaction.builder().accountId(1).build());
        assertEquals(3, countTopic.readKeyValuesToMap().get(1));

        inputTopic.pipeInput(2,Transaction.builder().accountId(2).build());
        assertEquals(1, countTopic.readKeyValuesToMap().get(2));
    }

}
