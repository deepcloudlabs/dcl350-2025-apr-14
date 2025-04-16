package com.example.hr.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class HrEventPublisherWebSocketService implements WebSocketHandler {
	private final Map<String,WebSocketSession> sessions = new ConcurrentHashMap<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session);
		System.out.println("New session is created with session id: %s".formatted(session.getId()));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println("An error has occured at the session (%s): %s".formatted(session.getId(),exception.getMessage()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		sessions.remove(session.getId());
		System.out.println("The session is closed with session id: %s".formatted(session.getId()));
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	@EventListener
	public void handleEvent(String eventAsJson) {
		var message = new TextMessage(eventAsJson);
		sessions.forEach((sessionId,session) -> {
			try {
				session.sendMessage(message);
			} catch (IOException e) {
				System.out.println("An error has occured at the session (%s): %s".formatted(sessionId,e.getMessage()));				
			}
		});
	}
}
