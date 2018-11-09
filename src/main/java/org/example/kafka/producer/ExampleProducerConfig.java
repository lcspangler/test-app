package org.example.kafka.producer;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class ExampleProducerConfig {
    private static final Logger log = LogManager.getLogger(ExampleProducerConfig.class);

    private final String bootstrapServers;
    private final String topic;
    private String acks = "1";
    private final String sender;

    public ExampleProducerConfig(String bootstrapServers, String topic,  String sender) {
        this.bootstrapServers = bootstrapServers;
        this.topic = topic;
        this.sender = sender;
    }

    public static ExampleProducerConfig fromEnv() {
        String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
        String topic = System.getenv("TOPIC_1");
        String sender = System.getenv("SENDER");

        return new ExampleProducerConfig(bootstrapServers, topic, sender);
    }

    public static Properties createProperties(ExampleProducerConfig config) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, config.getAcks());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");

        return props;
    }

	public String getAcks() {
		return acks;
	}

	public void setAcks(String acks) {
		this.acks = acks;
	}

	public String getBootstrapServers() {
		return bootstrapServers;
	}

	public String getTopic() {
		return topic;
	}

	public String getSender() {
		return sender;
	}
    
    
}
