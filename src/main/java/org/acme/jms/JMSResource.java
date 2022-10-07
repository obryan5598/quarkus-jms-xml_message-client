package org.acme.jms;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * A simple resource sending XML-based-content messages
 */
@Path("/messages")
public class JMSResource {

    @Inject
    JMSXMLConsumer consumer;

    @Inject
    JMSXMLProducer producer;

    @GET
    @Path("consume/first")
    @Produces(MediaType.TEXT_PLAIN)
    public void consumeFirst() {
        consumer.consume(1);
    }

    @GET
    @Path("consume/{quantity}")
    @Produces(MediaType.TEXT_PLAIN)
    public void consumeMessages(@PathParam ("quantity") Integer quantity) {
        consumer.consume(quantity);
    }

    @GET
    @Path("produce/{quantity}")
    @Produces(MediaType.TEXT_PLAIN)
    public void produce(@PathParam ("quantity") Integer quantity) {
        for (int i = 0; i < quantity; i++) {
	       	producer.produceMessages();
	    }
    }
}
