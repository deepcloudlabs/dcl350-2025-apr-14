package com.example.service;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
	private final IntegrationService integrationService;
	
	public BusinessService(IntegrationService integrationService) {
		this.integrationService = integrationService;
		System.out.println(this.integrationService.getClass());
	}

	@Scheduled(fixedRate = 10_000)
	public void doBusiness() {
		System.out.println("[%s] Received: %d".formatted(new Date(),integrationService.callRemoteService()));
	}
}
