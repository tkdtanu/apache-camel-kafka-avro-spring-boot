package com.tkd.camel.router;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//@Component
public class MyFirstRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer:test-timer").log("Message Logger Before Transform ->${body}").transform()
				.constant("Test Constant Message, Time:" + LocalDateTime.now())
				.log("Message Logger After Transform ->${body}")
				// If there is a single method within the bean, no need to mention the method
				// name
				// if there are multiple method, we need to specify
				.bean("getCurrentTimeBean", "getCurrentTime")
				.process("testTimeProcessor")
				.to("log:test-timer");
	}

}
