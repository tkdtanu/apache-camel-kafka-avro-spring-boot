package com.tkd.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.consumer.KafkaManualCommit;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaManualCommitProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Committing Kafka Message Manually");
		KafkaManualCommit manual = exchange.getIn().getHeader(KafkaConstants.MANUAL_COMMIT, KafkaManualCommit.class);
		manual.commit();
		log.info("Comitted Kafka Message Successfully");
	}

}
