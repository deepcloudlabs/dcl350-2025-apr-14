package com.example.lottery.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lottery.dto.LotteryNumberDTO;

@FeignClient(name = "lottery",path = "/api/v1")
public interface LotteryService {
	@GetMapping(value="/numbers",params= {"column"})
	public List<LotteryNumberDTO> getNumbers(@RequestParam int column);
}
