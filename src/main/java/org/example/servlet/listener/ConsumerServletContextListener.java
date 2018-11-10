package org.example.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.example.kafka.consumer.ExampleConsumer;

public class ConsumerServletContextListener implements ServletContextListener {// implements ServletContextListener {

	ExampleConsumer consumer = new ExampleConsumer();

	public void contextInitialized(ServletContextEvent contextEvent) {
		consumer.consume();
	}

	public void contextDestroyed(ServletContextEvent contextEvent) {
		consumer.closeConsumer();
	}

}
