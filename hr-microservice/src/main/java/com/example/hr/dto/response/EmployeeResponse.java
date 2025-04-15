package com.example.hr.dto.response;

import java.util.List;

import com.example.ddd.DataTransferObject;
import com.example.hr.domain.FiatCurrency;

@DataTransferObject
public record EmployeeResponse(
		String identity,
		String firstName,
		String lastName,
		String iban,
		double salary,
		FiatCurrency currency,
		List<String> departments,
		String jobStyle,
		int birthYear
) {

}
