package com.example.hr.application.business;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.domain.event.EmployeeFiredEvent;
import com.example.hr.domain.event.EmployeeHiredEvent;
import com.example.hr.hexagonal.Application;
import com.example.hr.infra.EventPublisher;
import com.example.hr.repository.EmployeeRepository;

@Application(api = HrApplication.class)
public class StandardHrApplication implements HrApplication {
	private final EmployeeRepository employeeRepository;
    private final EventPublisher eventPublisher;
    
	public StandardHrApplication(EmployeeRepository employeeRepository, EventPublisher eventPublisher) {
		this.employeeRepository = employeeRepository;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public Employee hireEmployee(Employee employee) {
		Objects.requireNonNull(employee);
		var identity = employee.getIdentity();
		if (employeeRepository.exists(identity))
			throw new IllegalArgumentException(
					"Cannot hire: Employee (%s) already exists.".formatted(identity.getValue()));
		var persistedEmployee = employeeRepository.persist(employee);
		var event = new EmployeeHiredEvent(persistedEmployee);
		eventPublisher.publishEvent(event);
		return persistedEmployee;
	}

	@Override
	public Optional<Employee> fireEmployee(TcKimlikNo identity) {
		var employee = employeeRepository.findById(identity);
		Consumer<Employee> fireEmployee = employeeRepository::remove;
		Consumer<Employee> publishEvent = firedEmployee -> eventPublisher.publishEvent(new EmployeeFiredEvent(firedEmployee));
		employee.ifPresent(fireEmployee.andThen(publishEvent));
		return employee;
	}

	@Override
	public Optional<Employee> findEmployeeByIdentity(TcKimlikNo identity) {
		return employeeRepository.findById(identity);
	}

}
