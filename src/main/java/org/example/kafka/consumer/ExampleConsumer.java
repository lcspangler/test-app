package org.example.kafka.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
		String recieved = "";
		// consumer.subscribe(Collections.singletonList(config.getTopic()));
		List<String> topics = new ArrayList<String>();
		topics.add("my-topic");
		consumer.subscribe(topics);

		log.info("Subscribed to topics: {}", topics);

		while (true) {
			log.info("Consuming records");
			ConsumerRecords<String, String> records = consumer.poll(1000);
			log.info("Records received: {}", records);

			int i = 0;
			for (ConsumerRecord<String, String> record : records) {
				log.info("Message received: {}", record);
				i++;
				if (i > 1)
					recieved += "\t\t\t" + record.value() + "\n";
				else
					recieved += record.value() + "\n";
				log.debug(record.key());

				if (commit) {
					consumer.commitSync();
				}
			}
			return recieved;
		}
	}

	public void closeConsumer() {
		consumer.close();
	}

}
