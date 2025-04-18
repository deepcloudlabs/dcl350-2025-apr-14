package com.example.securitycard.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value="messagingStrategy", havingValue = "kafka")
public class HrEventKafkaConsumerService {

	@KafkaListener(topics = {"${hr-topic}"},groupId = "security-card")
	public void handleHrEvent(String hrEvent) {
		System.out.println("New event has arrived from the topic: %s".formatted(hrEvent));
	}
}
