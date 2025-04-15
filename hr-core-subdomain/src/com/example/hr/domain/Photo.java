package com.example.hr.domain;

import java.util.Base64;
import java.util.Objects;

import com.example.ddd.ValueObject;

@ValueObject
public record Photo(byte[] values) {

	public static Photo of(byte[] values) {
		Objects.requireNonNull(values);
		return new Photo(values);
	}

	public static Photo of(String base64Values) {
		Objects.requireNonNull(base64Values);
		return new Photo(base64Values);
	}

	public Photo(String base64Values) {
		this(Base64.getDecoder().decode(base64Values));
	}

	@Override
	public String toString() {
		return toBase64String();
	}

	private String toBase64String() {
		return Base64.getEncoder().encodeToString(values);
	}

}
