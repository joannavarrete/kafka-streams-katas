package com.joannava.kafka.katas.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;

import static com.joannava.kafka.katas.utils.PropertiesUtils.*;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

public class TransactionProducer {

    private final KafkaProducer<Integer, String> producer;

    public TransactionProducer() {

        Properties appProperties = getProperties();

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, appProperties.getProperty(BOOTSTRAP_SERVERS_CONFIG));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(properties);
    }

    protected KafkaProducer<Integer,String> getProducer(){
        return this.producer;
    }

    public void send(KeyValue<Integer, String> keyValue){
        getProducer().send(new ProducerRecord<Integer,String>("transactions", keyValue.key, keyValue.value));
    }

    public void close(){
        getProducer().flush();
        getProducer().close();
    }
}
