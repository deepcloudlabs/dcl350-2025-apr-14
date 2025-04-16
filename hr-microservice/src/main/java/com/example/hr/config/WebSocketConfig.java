package com.example.hr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.hr.service.HrEventPublisherWebSocketService;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	private final HrEventPublisherWebSocketService eventPublisherWebSocketService;
	
	public WebSocketConfig(HrEventPublisherWebSocketService eventPublisherWebSocketService) {
		this.eventPublisherWebSocketService = eventPublisherWebSocketService;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(eventPublisherWebSocketService, "/events")
		        .setAllowedOrigins("*");
	}

}
