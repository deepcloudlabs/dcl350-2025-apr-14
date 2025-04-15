package com.example.hr.domain.event;

import com.example.hr.domain.Employee;

public final class EmployeeHiredEvent extends HrEvent {

	public EmployeeHiredEvent(Employee employee) {
		super(employee.getIdentity(), EventType.EMPLOYEE_HIRED_EVENT);
	}

}
