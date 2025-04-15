package com.example.hr.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FiatCurrencyTest {

	@ParameterizedTest
	@CsvSource(value = {
			"YTL,0",
			"EUR,1",
			"USD,2"
	})
	void createFiatCurrencySuccessfuly(String value,int ordinal) {
		var currency = FiatCurrency.valueOf(value);
		assertNotNull(currency);
		assertEquals(value, currency.name());
		assertEquals(ordinal, currency.ordinal());
	}

}
