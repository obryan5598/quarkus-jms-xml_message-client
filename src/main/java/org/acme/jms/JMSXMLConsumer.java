package org.acme.jms;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * A bean consuming messages from the JMS queue.
 */
@ApplicationScoped
public class JMSXMLConsumer {

    @Inject
    ConnectionFactory connectionFactory;

    @ConfigProperty(name = "quarkus.artemis.destination")
    String queueName;

    private String messageContent;


    public String getMessageContent() {
        return messageContent;
    }

    public void consume(Integer num) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue(queueName));
            for (int i = 0; i < num; i++) {
                Message message = consumer.receive();
                if (message == null || message.getBody(String.class).isEmpty()) {
                    // receive returns `null` if the JMSConsumer is closed
                    return;
                }
                messageContent = message.getBody(String.class);
                System.out.println("Consuming message from queue " + queueName + " with content: ");
                System.out.println(messageContent);
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
