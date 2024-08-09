package com.tkd.camel.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tkd.camel.exception.CustomException;
import com.tkd.camel.processor.ErrorProcessor;
import com.tkd.camel.processor.ExceptionProcessor;
import com.tkd.camel.processor.KafkaAvroMessageProcessor;
import com.tkd.camel.processor.KafkaManualCommitProcessor;
import com.tkd.camel.processor.RedeliveryProcessor;

@Component
public class KafkaRouter extends RouteBuilder {
	@Autowired
	private KafkaAvroMessageProcessor kafkaMessageProcessor;

	@Autowired
	private KafkaManualCommitProcessor kafkaManualCommitProcessor;

	@Autowired
	private ExceptionProcessor exceptionProcessor;

	@Autowired
	private RedeliveryProcessor redeliveryProcessor;

	@Autowired
	private ErrorProcessor errorProcessor;

	@Override
	public void configure() throws Exception {
		// Exception Handler
		// In case of Any Exception which are subclass of CustomException.class
		// it will try to re-attemp for 3 time and there will
		// be a delay of 5sec after every failure
		onException(CustomException.class).onExceptionOccurred(exceptionProcessor).maximumRedeliveries(3)
				.redeliveryDelay(5000).onRedelivery(redeliveryProcessor).handled(true).end();

		// Error Handler
		// In case of Any uncaught Exception, it will try to Send to DeadLetter
		// And while sending to DeadLetter if it fails, then re-attempt for 3 time and
		// there will be a delay of 5sec after every failure
		errorHandler(deadLetterChannel("direct:dead").onExceptionOccurred(errorProcessor).maximumRedeliveries(3)
				.redeliveryDelay(5000).onRedelivery(redeliveryProcessor));

		from("{{camel-properties.kafka.consumer.topic}}").routeId("kafkaConsumer")
				.log("Message Received from Kafka Topic=>headers: ${headers}")
				.log("Message Received from Kafka Topic=>body: ${body}").process(kafkaMessageProcessor)
				.process(kafkaManualCommitProcessor).to("log:error-handling").to("log:avro-messages");

		from("direct:dead").log("Received DeadLetter Message ${headers}=>${body}");
	}

}
