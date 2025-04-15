package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.ValueObject;

@ValueObject
public record Salary(double value, FiatCurrency currency) {
	public static Salary of(double value, FiatCurrency currency) {
		if (value <= 0.0)
			throw new IllegalArgumentException("Salary must be positive");
		Objects.nonNull(currency);
		return new Salary(value, currency);
	}

	public static Salary of(double value, String currencyString) {
		if (value <= 0.0)
			throw new IllegalArgumentException("Salary must be positive");
		Objects.nonNull(currencyString);
		var currency = FiatCurrency.valueOf(currencyString);
		return new Salary(value, currency);
	}
}
