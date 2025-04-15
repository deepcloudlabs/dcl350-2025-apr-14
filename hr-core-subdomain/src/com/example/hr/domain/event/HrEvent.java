package com.example.hr.domain.event;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.example.ddd.DomainEvent;
import com.example.hr.domain.TcKimlikNo;

@DomainEvent
public sealed class HrEvent permits EmployeeFiredEvent, EmployeeHiredEvent {
	private static final AtomicLong sequence = new AtomicLong();
	private final String eventId = UUID.randomUUID().toString(); 
	private final long timestamp = ZonedDateTime.now().toEpochSecond();
	private final long sequenceNo = sequence.getAndIncrement();
	private final TcKimlikNo identity;
	private final EventType eventType;
	public HrEvent(TcKimlikNo identity, EventType eventType) {
		this.identity = identity;
		this.eventType = eventType;
	}
	public String getEventId() {
		return eventId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public long getSequenceNo() {
		return sequenceNo;
	}
	public TcKimlikNo getIdentity() {
		return identity;
	}
	public EventType getEventType() {
		return eventType;
	}
	@Override
	public String toString() {
		return "HrEvent [eventId=" + eventId + ", timestamp=" + timestamp + ", sequenceNo=" + sequenceNo + ", identity="
				+ identity + ", eventType=" + eventType + "]";
	}
	
}
