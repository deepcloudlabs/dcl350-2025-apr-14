package com.example.lottery.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AutoRefresher {
	private final String host;
	private final String context;
	private final String path;
	private final int port;
	private final RestTemplate restTemplate;

	public AutoRefresher(RestTemplate restTemplate, 
			@Value("${server.port}") int port, 
			@Value("${server.address}") String host, 
			@Value("${server.servlet.context-path}") String context, 
			@Value("${spring.mvc.servlet.path}") String path) {
		this.host = host;
		this.context = context;
		this.path = path;
		this.port = port;
		this.restTemplate = restTemplate;
	}
	
	//@Scheduled(fixedRateString = "${refreshPeriod}")
	public void autoRefresh() {
		restTemplate.postForEntity("http://%s:%d/%s/%s/actuator/refresh".formatted(host,port,context,path), new EmptyBody(),String.class);
	}
}
record EmptyBody() {}