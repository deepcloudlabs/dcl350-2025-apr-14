package com.example.hr.domain;

public record Rate(double value) {
	public double getPercent() {
		return 1.* + value/100.0;
	}

	public static Rate of(double value) {
		if (value <= 0)
			throw new IllegalArgumentException("Rate (%f) cannot be less than or equal to zero.".formatted(value));
		return new Rate(value);
	}
}
