package com.example.service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

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
	
	@RateLimiter(name="integrationService2")
	public int callRemoteService2() {
		System.out.println("remoteService2: %s".formatted(new Date()));
		return 549;
	}
	
	@TimeLimiter(name="integrationService3",fallbackMethod = "fallbackForCallRemoteService3")
	public CompletableFuture<Integer> callRemoteService3() {
		return CompletableFuture.supplyAsync(()->{
			try {TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 6));} catch (InterruptedException e) {}
			return 3615;
		});
	}
	
	
	@CircuitBreaker(name="integrationService4")
	@Bulkhead(name="integrationService4",type = Type.SEMAPHORE)
	public int callRemoteService4() {
		System.out.println("remoteService4: %s".formatted(new Date()));
		return 549;
	}
	
	public int fallbackForCallRemoteService(Throwable t) {
		return 108;
	}
	
	public CompletableFuture<Integer>  fallbackForCallRemoteService3(Throwable t) {
		return CompletableFuture.supplyAsync(()->{
			return 108;
		});	}
}
