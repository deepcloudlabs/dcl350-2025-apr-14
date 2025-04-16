package com.example.hr.adapter;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.hr.domain.event.HrEvent;
import com.example.hr.hexagonal.Adapter;
import com.example.hr.infra.EventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Adapter(source =EventPublisher.class,target=ApplicationEventPublisher.class )
public class HrEventPublisherInternalAdapter implements EventPublisher {
	private final ApplicationEventPublisher eventPublisher;
	private final ObjectMapper objectMapper;
	
	public HrEventPublisherInternalAdapter(ApplicationEventPublisher eventPublisher, ObjectMapper objectMapper) {
		this.eventPublisher = eventPublisher;
		this.objectMapper = objectMapper;
	}

	@Override
	public void publishEvent(HrEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			eventPublisher.publishEvent(eventAsJson);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Cannot convert event to json: %s".formatted(e.getMessage()));
		}
	}

}
