package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.ValueObject;

// immutable
@ValueObject
public record FullName(String firstName, String lastName) {
	public static FullName valueOf(String firstName, String lastName) {
		Objects.nonNull(firstName);
		Objects.nonNull(lastName);
		return new FullName(firstName, lastName);
	}
}
