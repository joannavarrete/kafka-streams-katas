package com.joannava.kafka.katas;

import static com.joannava.kafka.katas.utils.PropertiesUtils.getProperties;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

public class TopologyExecutor {

    private KafkaStreams streams;

    public TopologyExecutor(String applicationId, Topology topology) {

        Properties appProperties = getProperties();

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        config.put(BOOTSTRAP_SERVERS_CONFIG, appProperties.getProperty(BOOTSTRAP_SERVERS_CONFIG));
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        // config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        streams = new KafkaStreams(topology, config);

    }

    public void execute() {

        streams.start();

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
