package com.joannava.kafka.katas;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static com.joannava.kafka.katas.PropertiesUtils.*;

public class TransactionProducer {

    private final KafkaProducer<String, String> producer;

    public TransactionProducer() {

        Properties appProperties = getProperties();
        
        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, appProperties.getProperty(BOOTSTRAP_SERVERS_CONFIG));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(properties);
    }

    protected KafkaProducer<String,String> getProducer(){
        return this.producer;
    }

    public void send(String message){
        getProducer().send(new ProducerRecord<String,String>("transactions", message));
    }

    public void close(){
        getProducer().flush();
        getProducer().close();
    }
}
