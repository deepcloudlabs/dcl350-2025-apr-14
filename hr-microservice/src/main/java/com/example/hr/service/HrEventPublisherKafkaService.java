package com.example.hr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value="messagingStrategy", havingValue = "kafka")
public class HrEventPublisherKafkaService {
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final String hrEventsTopic;

	public HrEventPublisherKafkaService(KafkaTemplate<String, String> kafkaTemplate,
			@Value("${topicName}") String hrEventsTopic) {
		this.kafkaTemplate = kafkaTemplate;
		this.hrEventsTopic = hrEventsTopic;
	}

	@EventListener
	public void handleEvent(String eventAsJson) {
		kafkaTemplate.send(hrEventsTopic, eventAsJson);
	}

}
