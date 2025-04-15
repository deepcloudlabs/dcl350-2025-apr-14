package com.example.hr.domain;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class TcKimlikNoTest {

	@ParameterizedTest
	@CsvFileSource(resources = "valid-tckimlikno.csv")
	void createTcKimlikNoSuccessfuly(String value) {
		var identity = TcKimlikNo.of(value);
		assertNotNull(identity);
		assertEquals(value, identity.getValue());
	}

	@ParameterizedTest
	@CsvFileSource(resources = "invalid-tckimlikno.csv")
	void createTcKimlikNoFails(String value) throws Throwable {
		assertThrows(IllegalArgumentException.class, ()->TcKimlikNo.of(value));
	}
}
