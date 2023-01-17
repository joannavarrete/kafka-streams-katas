package com.joannava.kafka.katas.aggregations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonDeserializer;
import com.joannava.kafka.katas.serdes.JacksonSerializer;

public class NetProfitTopologyTest {

    private final NetProfitTopology topology = new NetProfitTopology();

    private TopologyTestDriver tp;

    private TestInputTopic<Integer, Transaction> inputTopic;
    private TestOutputTopic<Integer, BigDecimal> outputTopic;

    @BeforeEach
    public void beforeEach() {
        tp = new TopologyTestDriver(topology.build());
        // setup test topics
        inputTopic = tp.createInputTopic("transactions", new IntegerSerializer(), new JacksonSerializer<Transaction>());
        outputTopic = tp.createOutputTopic("net_profit_by_account", new IntegerDeserializer(), new JacksonDeserializer<>(BigDecimal.class));
    }

    @AfterEach
    public void tearDown() {
        tp.close();
    }

    @Test
    public void shouldComputeNetProfitByAccount() {
        inputTopic.pipeInput(3334, Transaction.builder().transactionCode("sell").total(new BigDecimal("50")).build());
        inputTopic.pipeInput(3334, Transaction.builder().transactionCode("sell").total(new BigDecimal("50")).build());
        inputTopic.pipeInput(3334, Transaction.builder().transactionCode("sell").total(new BigDecimal("50")).build());
        inputTopic.pipeInput(3334, Transaction.builder().transactionCode("buy").total(new BigDecimal("50")).build());
        inputTopic.pipeInput(444, Transaction.builder().transactionCode("sell").total(new BigDecimal("50")).build());
        inputTopic.pipeInput(444, Transaction.builder().transactionCode("buy").total(new BigDecimal("50")).build());

        Map<Integer, BigDecimal> keyValues = outputTopic.readKeyValuesToMap();
        assertEquals(100, keyValues.get(3334).intValue());
        assertEquals(0, keyValues.get(444).intValue());
    }

}
