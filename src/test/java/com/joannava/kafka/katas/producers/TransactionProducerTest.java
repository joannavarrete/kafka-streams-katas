package com.joannava.kafka.katas.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Mocked;
import mockit.Tested;

public class TransactionProducerTest {

    @Mocked
    KafkaProducer<String, String> kafkaProducer;

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

        producer.send("Hello World");
        producer.close();

        new FullVerifications() {
            {
                kafkaProducer.send(new ProducerRecord<String, String>("transactions", "Hello World"));
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
