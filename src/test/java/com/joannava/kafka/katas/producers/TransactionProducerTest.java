package com.joannava.kafka.katas.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.KeyValue;
import org.junit.jupiter.api.Test;

import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Mocked;
import mockit.Tested;

public class TransactionProducerTest {

    @Mocked
    KafkaProducer<Integer, String> kafkaProducer;

    @Tested
    private TransactionProducer producer = new TransactionProducer();

    @Test
    public void whenSendingAmessageThetopicShouldBeTransactionsAndShouldCallSendAndFlushAndClose() {
        new Expectations(producer) {
            {
                producer.getProducer();
                result = kafkaProducer;
            }
        };

        producer.send(KeyValue.pair(1, "hello world"));
        producer.close();

        new FullVerifications() {
            {
                kafkaProducer.send(new ProducerRecord<Integer, String>("transactions", 1, "hello world"));
                times = 1;

                kafkaProducer.flush();
                times = 1;

                kafkaProducer.close();
                ;
                times = 1;
            }
        };

    }
}
