package com.tkd.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component("testTimeProcessor")
@Slf4j
class TestTimeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Processing Message:{}", exchange.getMessage().getBody());
	}

}