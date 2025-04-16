package com.example.hr.dto.request;

import java.util.List;

import com.example.hr.domain.FiatCurrency;
import com.example.validation.Iban;
import com.example.validation.TcKimlikNo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HireEmployeeRequest(
		@TcKimlikNo String identity,
		@NotBlank String firstName,
		@NotBlank String lastName,
		@Iban String iban,
		@Positive double salary,
		@NotNull FiatCurrency currency,
		List<String> departments,
		@NotBlank String jobStyle,
		@Max(2009) int birthYear,
		@NotBlank String photo
		) {

}
