package com.tkd.camel.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedeliveryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Integer redeliveryAttempt = exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER, Integer.class);
		Integer maxRedliveryAttempt = exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER, Integer.class);
		log.info("Redelivery-Attempt Is:{}", redeliveryAttempt);
		
		if(redeliveryAttempt==maxRedliveryAttempt) {
			log.error("Reached Max Redelivery Attempt of Message");
		}
	}

}
