package com.joannava.kafka.katas.producers;

import static com.joannava.kafka.katas.utils.PropertiesUtils.getProperties;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;

public class JsonProducer<K> {

    private final KafkaProducer<K, String> producer;

    public JsonProducer(final Serializer<K> serializer) {

        Properties appProperties = getProperties();

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, appProperties.getProperty(BOOTSTRAP_SERVERS_CONFIG));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializer.getClass().getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(properties);
    }

    protected KafkaProducer<K, String> getProducer() {
        return this.producer;
    }

    public void send(String topic, KeyValue<K, String> keyValue) {
        getProducer().send(new ProducerRecord<K, String>(topic, keyValue.key, keyValue.value));
    }

    public void close() {
        getProducer().flush();
        getProducer().close();
    }
}
