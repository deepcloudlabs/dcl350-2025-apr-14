package com.example.crm.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.document.CustomerDocument;
import com.example.crm.event.CustomerAcquiredEvent;
import com.example.crm.event.CustomerReleasedEvent;
import com.example.crm.repository.CustomerDocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveCrmService {
	private final CustomerDocumentRepository customerDocumentRepository;
	private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaTemplate;
	private final ObjectMapper objectMapper;

	public ReactiveCrmService(CustomerDocumentRepository customerDocumentRepository,
			ReactiveKafkaProducerTemplate<String, String> reactiveKafkaTemplate, ObjectMapper objectMapper) {
		this.customerDocumentRepository = customerDocumentRepository;
		this.reactiveKafkaTemplate = reactiveKafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public Mono<CustomerDocument> findCustomerByEmail(String email) {
		return customerDocumentRepository.findById(email);
	}

	public Flux<CustomerDocument> findCustomers(int pageNo, int pageSize) {
		return customerDocumentRepository.findAll(PageRequest.of(pageNo, pageSize));
	}

	public Mono<CustomerDocument> addCustomer(CustomerDocument customer) {
		return customerDocumentRepository.insert(customer)
				.doOnSuccess(_ -> sendCustomerAcquiredEvent(customer.getEmail()));
	}

	public Mono<CustomerDocument> updateCustomer(String email, CustomerDocument customer) {
		return customerDocumentRepository.save(customer);
	}

	public Mono<CustomerDocument> removeCustomerByEmail(String email) {
		return customerDocumentRepository.findById(email).doOnSuccess(_ -> {
			customerDocumentRepository.deleteById(email).doOnSuccess(_ -> sendCustomerReleasedEvent(email));
		});
	}

	private void sendCustomerAcquiredEvent(String email) {
		try {
			var event = new CustomerAcquiredEvent(email);
			var eventAsJson = objectMapper.writeValueAsString(event);
			reactiveKafkaTemplate.send("crm-events", "", eventAsJson).subscribe(_ -> {
				System.out.println("The event is successfully sent to the Kafka server.");
			});
		} catch (JsonProcessingException e) {
		}
	}

	private void sendCustomerReleasedEvent(String email) {
		try {
			var event = new CustomerReleasedEvent(email);
			var eventAsJson = objectMapper.writeValueAsString(event);
			reactiveKafkaTemplate.send("crm-events", "", eventAsJson).subscribe(_ -> {
				System.out.println("The event is successfully sent to the Kafka server.");
			});
		} catch (JsonProcessingException e) {
		}
	}

}
