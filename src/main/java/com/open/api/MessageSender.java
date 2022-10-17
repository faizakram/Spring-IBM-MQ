package com.open.api;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Component
public class MessageSender {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${ibm.mq.queue}")
	private String destination;

	public void sendMessage() {
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("ABC");
			}
		});
	}

	public String sendMessageAndReceive() {
		this.jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		this.jmsTemplate.convertAndSend(destination, "bar");
		try {
			String value = this.jmsTemplate.execute(session -> {
				MessageConsumer consumer = session.createConsumer(
						this.jmsTemplate.getDestinationResolver().resolveDestinationName(session, destination, false));
				String result;
				try {
					Message received = consumer.receive(5000);
					result = (String) this.jmsTemplate.getMessageConverter().fromMessage(received);
					// Do some stuff that might throw an exception
					received.acknowledge();
				} finally {
					consumer.close();
				}
				return result;
			}, true);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
