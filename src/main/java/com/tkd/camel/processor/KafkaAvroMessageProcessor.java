package com.tkd.camel.processor;

import java.util.random.RandomGenerator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.tkd.camel.avro.SampleUserAvro;
import com.tkd.camel.exception.AvroProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component("kafkaMessageProcessor")
@Slf4j
public class KafkaAvroMessageProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		log.info("Processing Kafka AVRO Message:{}", exchange.getIn().getBody());

		SampleUserAvro body = (SampleUserAvro) exchange.getIn().getBody();
		log.info("AVRO Headers:{}", body.getHeader());

		// Just to replicate Failure Scenario
		if (RandomGenerator.getDefault().nextInt(0, 5) % 2 == 0) {
			log.info("Thorwing RuntimeException Manually");
			throw new IllegalArgumentException("Unknown Error happened");
		}
		if (RandomGenerator.getDefault().nextBoolean()) {
			log.info("Thorwing AvroProcessingException Manually");
			throw new AvroProcessingException("Invalid Channel:" + body.getHeader().getChannelType().toString());
		}
		log.info("Successfully Processed AVRO Message");
	}

}
