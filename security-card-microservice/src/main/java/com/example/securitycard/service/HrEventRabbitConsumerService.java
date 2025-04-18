package com.example.securitycard.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value="messagingStrategy", havingValue = "amqp")
public class HrEventRabbitConsumerService {

	@RabbitListener(queues = {"${queueName}"})
	public void handleHrEvent(String hrEvent) {
		System.out.println("New event has arrived from the queue: %s".formatted(hrEvent));
	}
}
