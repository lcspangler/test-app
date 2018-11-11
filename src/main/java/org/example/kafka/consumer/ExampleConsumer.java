package org.example.kafka.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleConsumer {
	private static final Logger log = LogManager.getLogger(ExampleConsumer.class);
	private ExampleConsumerConfig config;
	private KafkaConsumer consumer;
	private Properties props;
	private boolean commit;

	public ExampleConsumer() {
		config = ExampleConsumerConfig.fromEnv();
		props = ExampleConsumerConfig.createProperties(config);
		consumer = new KafkaConsumer(props);
		commit = !Boolean.parseBoolean(config.getEnableAutoCommit());
	}

	@SuppressWarnings("unchecked")
	public String consume() {
		List<String> topics = new ArrayList<String>();
		topics.add("my-topic");
		consumer.subscribe(topics);

		log.info("Subscribed to topics: {}", consumer.listTopics());

		while (true) {
			log.info("Retrieving records");
			ConsumerRecords<String, String> records = consumer.poll(1000);
			consumer.listTopics();

			if (records.isEmpty()) {
				log.info("Found no records");
				continue;
			}

			log.info("Total No. of records received : {}", records.count());
			for (ConsumerRecord<String, String> record : records) {
				log.info("Record received partition : {}, key : {}, value : {}, offset : {}", record.partition(),
						record.key(), record.value(), record.offset());
			}

			List<TopicPartition> partitions = consumer.partitionsFor("my-topic");
			for (TopicPartition partition : partitions) {
				log.info("Now at: {}", consumer.position(partition));
			}
		}
	}

	public void closeConsumer() {
		consumer.close();
	}

}
