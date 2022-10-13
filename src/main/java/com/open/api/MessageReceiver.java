package com.open.api;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")

public class MessageReceiver {

	@Autowired
	private JmsTemplate jmsTemplate;

	@JmsListener(destination = "ibm.mq.queue")
	public String readMessage() throws JMSException {
		System.out.println("Recieve");
		String message = null;
		Object msg = jmsTemplate.receiveAndConvert();
		System.out.println("Message:::: " + msg.toString());
		if (msg instanceof TextMessage) {
			message = ((TextMessage) msg).getText();
			System.out.println("Message" + message);
		}

		return message;
	}

}
