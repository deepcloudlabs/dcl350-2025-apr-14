package com.example.lottery.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.example.lottery.model.LotteryModel;

@Service
@RefreshScope
public class LotteryService {
	private final int lotteryMin;
	private final int lotteryMax;
	private final int lotterySize;
	
	public LotteryService(
			@Value("${lotteryMin}") int lotteryMin, 
			@Value("${lotteryMax}") int lotteryMax, 
			@Value("${lotterySize}") int lotterySize) {
		this.lotteryMin = lotteryMin;
		this.lotteryMax = lotteryMax;
		this.lotterySize = lotterySize;
	}
	
	public List<LotteryModel> draw(int column){
	    return IntStream.range(0, column).mapToObj(_ -> draw()).toList();	
	}
	
	public LotteryModel draw(){
		return new LotteryModel(
				    ThreadLocalRandom.current()
				                     .ints(lotteryMin, lotteryMax)
				                     .distinct()
				                     .limit(lotterySize)
				                     .sorted()
				                     .boxed()
				                     .toList()
				);
				
	}
}
