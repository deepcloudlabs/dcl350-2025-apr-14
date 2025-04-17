package com.example.lottery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Service
@ConditionalOnProperty(name = "loadbalancing",havingValue = "custom")
public class LotteryConsumerService {
	private final RestTemplate restTemplate;
	private final static String LOTTERY_REST_API_URL = "http://%s:%d/api/v1/numbers?column=%d";
	private final DiscoveryClient discoveryClient;
	private final List<ServiceInstance> lotteryServerInstances = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    
	public LotteryConsumerService(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	@PostConstruct
	public void retrieveLotteryInstances() {
		this.lotteryServerInstances.addAll(discoveryClient.getInstances("lottery"));
	}

	@Scheduled(fixedRate = 20_000)
	public void refreshInstances() {
		this.lotteryServerInstances.clear();
		this.lotteryServerInstances.addAll(discoveryClient.getInstances("lottery"));
	}
	
	@Scheduled(fixedRate = 5_000)
	public void callLotteryRestApi() {
		var lotteryInstance = nextInstance();
		var formattedUrl = LOTTERY_REST_API_URL.formatted(lotteryInstance.getHost(), lotteryInstance.getPort(), 5);
		try {
			var response = restTemplate.getForEntity(formattedUrl, String.class).getBody();			
			System.out.println("[%s]: %s".formatted(formattedUrl,response));
		}catch (RestClientException e) {
			refreshInstances();
		}
	}

	private ServiceInstance nextInstance() {
		return this.lotteryServerInstances.get(counter.getAndIncrement()%this.lotteryServerInstances.size());
	}
}
