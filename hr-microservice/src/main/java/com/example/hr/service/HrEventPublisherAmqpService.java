package com.example.hr.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value="messagingStrategy", havingValue = "amqp")
public class HrEventPublisherAmqpService {
	private final RabbitTemplate rabbitTemplate;
	private final String exchangeName;

	public HrEventPublisherAmqpService(RabbitTemplate rabbitTemplate,
			@Value("${exchangeName}") String exchangeName) {
		this.rabbitTemplate = rabbitTemplate;
		this.exchangeName = exchangeName;
	}

	@EventListener
	public void handleEvent(String eventAsJson) {
		rabbitTemplate.convertAndSend(exchangeName, null,eventAsJson);
	}

}
