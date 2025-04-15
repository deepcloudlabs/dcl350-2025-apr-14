package com.example.hr.infra;

import com.example.hr.domain.event.HrEvent;
import com.example.hr.hexagonal.Port;
import com.example.hr.hexagonal.PortType;

@Port(PortType.DRIVEN)
public interface EventPublisher {
	public void publishEvent(HrEvent event);
}
