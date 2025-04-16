package com.example.securitycard.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class HrEventConsumerService {

	@KafkaListener(topics = {"${hr-topic}"},groupId = "security-card")
	public void handleHrEvent(String hrEvent) {
		System.out.println("New event has arrived from the topic: %s".formatted(hrEvent));
	}
}
