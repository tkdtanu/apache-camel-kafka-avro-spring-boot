package com.tkd.camel.processor;

import static org.junit.Assert.assertNotNull;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExceptionProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
		assertNotNull(caused);
		log.error("Failure happened For RouteId:{}, Exchange:{}, Exception:{} ", exchange.getFromRouteId(),
				exchange.getExchangeId(), caused.getLocalizedMessage());
		caused.printStackTrace();
	}

}
