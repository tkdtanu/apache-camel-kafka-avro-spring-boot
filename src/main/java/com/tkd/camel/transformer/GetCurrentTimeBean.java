package com.tkd.camel.transformer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component("getCurrentTimeBean")
class GetCurrentTimeBean {

	public String getCurrentTime() {
		return "Time Now:" + LocalDateTime.now();
	}
}