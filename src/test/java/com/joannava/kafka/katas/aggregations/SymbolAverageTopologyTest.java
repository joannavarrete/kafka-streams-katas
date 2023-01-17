package com.joannava.kafka.katas.aggregations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonDeserializer;
import com.joannava.kafka.katas.serdes.JacksonSerializer;

public class SymbolAverageTopologyTest {

    private final SymbolAverageTopology topology = new SymbolAverageTopology();
    private TopologyTestDriver tp;

    private TestInputTopic<Integer, Transaction> inputTopic;
    private TestOutputTopic<String, BigDecimal> outputTopic;

    @BeforeEach
    public void beforeEach() {
        tp = new TopologyTestDriver(topology.build());
        // setup test topics
        inputTopic = tp.createInputTopic("transactions", new IntegerSerializer(), new JacksonSerializer<Transaction>());
        outputTopic = tp.createOutputTopic("symbol_average", new StringDeserializer(),
                new JacksonDeserializer<>(BigDecimal.class));
    }

    @AfterEach
    public void tearDown() {
        tp.close();
    }

    @Test
    public void shouldComputeAverageForSymbol() {
        inputTopic.pipeInput(3334, Transaction.builder().symbol("ebay").price(new BigDecimal("50")).build());
        inputTopic.pipeInput(3334, Transaction.builder().symbol("ebay").price(new BigDecimal("150")).build());
        inputTopic.pipeInput(3334, Transaction.builder().symbol("ebay").price(new BigDecimal("250")).build());
        inputTopic.pipeInput(3334, Transaction.builder().symbol("google").price(new BigDecimal("50")).build());

        Map<String, BigDecimal> keyValues = outputTopic.readKeyValuesToMap();
        assertEquals(150f, keyValues.get("ebay").floatValue());
        assertEquals(50f, keyValues.get("google").floatValue());
    }

}
