package org.acme.jms;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.io.*;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * A bean producing XML-based-content messages and sending them to the input JMS queue.
 */
@ApplicationScoped
public class JMSXMLProducer {

    @Inject
    ConnectionFactory connectionFactory;

    @ConfigProperty(name = "quarkus.artemis.destination")
    String queueName;

    String insertMessage;

    public JMSXMLProducer() {
	   this.insertMessage = getInsertMessage();

    }

    public void produceMessages() {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            JMSProducer producer = context.createProducer();
            producer.setProperty("operationName", "insertMessage");
            System.out.println("sending message to queue " + queueName);
            producer.send(context.createQueue(queueName), this.insertMessage);

        } catch (Exception ex) {
            System.out.println("ERRORE: " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    private String getMessage(String inputFile) {
        String contents = null;

        try (InputStream inputStream = getClass().getResourceAsStream(inputFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                contents = reader.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
        }
        catch (Exception ex) {
                System.out.println(ex.getMessage());
        }
        return contents;

    }
     private String getInsertMessage() {
    	return getMessage("/files/insertMessage.xml");
    }

}
