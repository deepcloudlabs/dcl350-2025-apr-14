package com.example.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class IntegrationService {

	@Retry(name = "integrationService", fallbackMethod = "fallbackForCallRemoteService")
	public int callRemoteService() {
		if (ThreadLocalRandom.current().nextInt(0, 10) < 7) {
			System.out.println("remote service is about to fail!");
			throw new RuntimeException("cannot access the remote service");
		}
		return 42;
	}
	
	public int fallbackForCallRemoteService(Throwable t) {
		return 108;
	}
}
