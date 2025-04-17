package com.example.hr.adapter;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.example.hr.document.EmployeeDocument;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.hexagonal.Adapter;
import com.example.hr.repository.EmployeeDocumentRepository;
import com.example.hr.repository.EmployeeRepository;

@Repository
@Adapter(source = EmployeeRepository.class, target = EmployeeDocumentRepository.class)
@ConditionalOnProperty(value = "persistenceStrategy", havingValue = "mongodb")
public class EmployeeRepositoryMongoAdapter implements EmployeeRepository {
	private final EmployeeDocumentRepository employeeDocumentRepository;
	private final ModelMapper modelMapper;

	public EmployeeRepositoryMongoAdapter(EmployeeDocumentRepository employeeDocumentRepository,
			ModelMapper modelMapper) {
		this.employeeDocumentRepository = employeeDocumentRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public boolean exists(TcKimlikNo identity) {
		return employeeDocumentRepository.existsById(identity.getValue());
	}

	@Override
	public Employee persist(Employee employee) {
		var employeeDocument = modelMapper.map(employee, EmployeeDocument.class);
		var savedEmployeeDocument = employeeDocumentRepository.insert(employeeDocument);
		return modelMapper.map(savedEmployeeDocument, Employee.class);
	}

	@Override
	public Optional<Employee> findById(TcKimlikNo identity) {
		return employeeDocumentRepository.findById(identity.getValue())
				.map(document -> modelMapper.map(document, Employee.class));
	}

	@Override
	public Optional<Employee> remove(Employee employee) {
		var identity = employee.getIdentity().getValue();
		var document = employeeDocumentRepository.findById(identity);
		document.ifPresent(employeeDocumentRepository::delete);
		return document.map(empDoc -> modelMapper.map(empDoc, Employee.class));
	}

}
