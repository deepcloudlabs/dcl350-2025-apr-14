package com.example.hr.adapter;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.entity.EmployeeEntity;
import com.example.hr.hexagonal.Adapter;
import com.example.hr.repository.EmployeeEntityRepository;
import com.example.hr.repository.EmployeeRepository;

@Repository
@Adapter(source = EmployeeRepository.class, target = EmployeeEntityRepository.class)
@ConditionalOnProperty(value="persistenceStrategy", havingValue = "jpa")
public class EmployeeRepositoryJpaAdapter implements EmployeeRepository {
	private final EmployeeEntityRepository employeeEntityRepository;
	private final ModelMapper modelMapper;

	public EmployeeRepositoryJpaAdapter(EmployeeEntityRepository employeeEntityRepository, ModelMapper modelMapper) {
		this.employeeEntityRepository = employeeEntityRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public boolean exists(TcKimlikNo identity) {
		return employeeEntityRepository.existsById(identity.getValue());
	}

	@Override
	@Transactional
	public Employee persist(Employee employee) {
		var employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
		var savedEmployeeEntity = employeeEntityRepository.save(employeeEntity);
		return modelMapper.map(savedEmployeeEntity, Employee.class);
	}

	@Override
	public Optional<Employee> findById(TcKimlikNo identity) {
		return employeeEntityRepository.findById(identity.getValue())
				.map(entity -> modelMapper.map(entity, Employee.class));
	}

	@Override
	@Transactional
	public Optional<Employee> remove(Employee employee) {
		var removedEmployee = employeeEntityRepository.findById(employee.getIdentity().getValue());
		removedEmployee.ifPresent(employeeEntityRepository::delete);
		return removedEmployee.map(entity -> modelMapper.map(entity, Employee.class));
	}

}
