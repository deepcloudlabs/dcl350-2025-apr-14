package com.example.hr.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.FireEmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;

@Service
public class HrService {
    private final HrApplication hrApplication;
	private final ModelMapper modelMapper;
    
	public HrService(HrApplication hrApplication, ModelMapper modelMapper) {
		this.hrApplication = hrApplication;
		this.modelMapper = modelMapper;
	}

	public EmployeeResponse findById(String identity) {
		var employee = hrApplication.findEmployeeByIdentity(TcKimlikNo.of(identity));
		return modelMapper.map(employee,EmployeeResponse.class);
	}

	@Transactional
	public HireEmployeeResponse hireEmployee(HireEmployeeRequest request) {
		var employee = modelMapper.map(request, Employee.class);
		hrApplication.hireEmployee(employee);
		return new HireEmployeeResponse("success", request.identity());
	}

	@Transactional
	public FireEmployeeResponse fireEmployee(String identity) {
		var firedEmployee = hrApplication.fireEmployee(TcKimlikNo.of(identity)).orElseThrow(() -> new IllegalArgumentException("Cannot fire: the employee (%s) does not exist.".formatted(identity)));
		return modelMapper.map(firedEmployee, FireEmployeeResponse.class);
	}

}
