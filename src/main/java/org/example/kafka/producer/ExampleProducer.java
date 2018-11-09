package org.example.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Properties;

public class ExampleProducer {
    private static final Logger log = LogManager.getLogger(ExampleProducer.class);
    ExampleProducerConfig config;
    KafkaProducer producer;
    Properties props;

    public ExampleProducer() {
        this.config = ExampleProducerConfig.fromEnv();
        this.props = ExampleProducerConfig.createProperties(config);
        this.producer = new KafkaProducer(props);
    }

    public void sendMessage(String message) throws InterruptedException {
        log.info("Sending message: {}", message);
        producer.send(new ProducerRecord(config.getTopic(),  message));
        log.info("Message sent.");
    }

    public void closeProducer() {
        producer.close();
    }
}
