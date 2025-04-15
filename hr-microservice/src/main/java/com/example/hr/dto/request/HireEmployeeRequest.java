package com.example.hr.dto.request;

import java.util.List;

import com.example.hr.domain.FiatCurrency;

public record HireEmployeeRequest(
		String identity,
		String firstName,
		String lastName,
		String iban,
		double salary,
		FiatCurrency currency,
		List<String> departments,
		String jobStyle,
		int birthYear,
		String photo
		) {

}
