package com.example.securitycard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import jakarta.annotation.PostConstruct;

@Service
public class HrWebSocketClientService implements WebSocketHandler{
	private final WebSocketClient webSocketClient;
	private final String hrWsUrl;
	
	public HrWebSocketClientService(WebSocketClient webSocketClient, 
			@Value("${hrWsUrl}") String hrWsUrl) {
		this.webSocketClient = webSocketClient;
		this.hrWsUrl = hrWsUrl;
	}

	@PostConstruct
	public void connectToWSHrServer() {
		webSocketClient.execute(this, hrWsUrl);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Successfully connected to the ws service with session id %s".formatted(session.getId()));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("New event has arrived from the ws endpoint: %s".formatted(message.getPayload()));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println("An error has occured at the session (%s): %s".formatted(session.getId(),exception.getMessage()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		System.out.println("The session is closed with session id: %s".formatted(session.getId()));		
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
