package com.example.hr.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.hr.domain.event.HrEvent;
import com.example.hr.hexagonal.Adapter;
import com.example.hr.infra.EventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Adapter(source =EventPublisher.class,target=KafkaTemplate.class )
public class HrEventPublisherKafkaAdapter implements EventPublisher {
	private final KafkaTemplate<String,String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private final String hrEventsTopic;
	
	public HrEventPublisherKafkaAdapter(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, 
			@Value("${topicName}") String hrEventsTopic) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
		this.hrEventsTopic = hrEventsTopic;
	}

	@Override
	public void publishEvent(HrEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			kafkaTemplate.send(hrEventsTopic, eventAsJson);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Cannot convert event to json: %s".formatted(e.getMessage()));
		}
	}

}
