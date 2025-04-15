package com.example.hr.domain.event;

import com.example.hr.domain.Employee;

public final class EmployeeFiredEvent extends HrEvent {
	private final Employee firedEmployee;

	public EmployeeFiredEvent(Employee employee) {
		super(employee.getIdentity(), EventType.EMPLOYEE_FIRED_EVENT);
		this.firedEmployee = employee;
	}

	public Employee getFiredEmployee() {
		return firedEmployee;
	}

}
