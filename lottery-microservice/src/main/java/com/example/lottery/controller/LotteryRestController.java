package com.example.lottery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.lottery.model.LotteryModel;
import com.example.lottery.service.LotteryService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/numbers")
@RequestScope
@Validated
@CrossOrigin
public class LotteryRestController {
	private final LotteryService lotteryService;
	private final int serverPort;

	public LotteryRestController(LotteryService lotteryService, @Value("${server.port}") int serverPort) {
		this.lotteryService = lotteryService;
		this.serverPort = serverPort;
	}

	@GetMapping(params = { "column" })
	public List<LotteryModel> getLotteryNumbers(@RequestParam @Min(3) @Max(25) int column) {
		System.out.println("New request has arrived at port %d".formatted(serverPort));
		return lotteryService.draw(column);
	}
}
