package com.example.hr.application;

import java.util.Optional;

import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.hexagonal.Port;
import com.example.hr.hexagonal.PortType;

@Port(PortType.DRIVING)
public interface HrApplication {
	Employee hireEmployee(Employee employee);

	Optional<Employee> fireEmployee(TcKimlikNo identity);

	Optional<Employee> findEmployeeByIdentity(TcKimlikNo identity);

}
