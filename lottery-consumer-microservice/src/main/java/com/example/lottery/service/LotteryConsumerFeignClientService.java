package com.example.lottery.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "loadbalancing",havingValue = "feign")
public class LotteryConsumerFeignClientService {
	private final LotteryService lotteryService;

	public LotteryConsumerFeignClientService(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}
	
	@Scheduled(fixedRate = 5_000)
	public void callLotteryService() {
	    System.out.println(lotteryService.getNumbers(5));
	}
}
