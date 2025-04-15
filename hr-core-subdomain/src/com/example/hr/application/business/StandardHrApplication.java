package com.example.hr.application.business;

import java.util.Objects;
import java.util.Optional;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.hexagonal.Application;
import com.example.hr.repository.EmployeeRepository;

@Application(api = HrApplication.class)
public class StandardHrApplication implements HrApplication {
	private final EmployeeRepository employeeRepository;

	public StandardHrApplication(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee hireEmployee(Employee employee) {
		Objects.requireNonNull(employee);
		var identity = employee.getIdentity();
		if (employeeRepository.exists(identity))
			throw new IllegalArgumentException(
					"Cannot hire: Employee (%s) already exists.".formatted(identity.getValue()));
		var persistedEmployee = employeeRepository.persist(employee);
		return persistedEmployee;
	}

	@Override
	public Optional<Employee> fireEmployee(TcKimlikNo identity) {
		var employee = employeeRepository.findById(identity);
		employee.ifPresent(firedEmployee -> employeeRepository.remove(firedEmployee));
		return employee;
	}

	@Override
	public Optional<Employee> findEmployeeByIdentity(TcKimlikNo identity) {
		return employeeRepository.findById(identity);
	}

}
