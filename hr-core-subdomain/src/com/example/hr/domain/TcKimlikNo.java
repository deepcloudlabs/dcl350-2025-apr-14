package com.example.hr.domain;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.example.ddd.ValueObject;

// Value Object: i. identity -> state
//              ii. belongs to an Entity class
//             iii. immutable? -> 
@ValueObject
public final class TcKimlikNo {
	private static final Map<String, TcKimlikNo> TC_KIMLIKNO_CACHE = new ConcurrentHashMap<>();
	private final String value;

	private TcKimlikNo(String value) {
		this.value = value;
	}

	public static TcKimlikNo of(String value) {
		// validation
		if (!isValid(value))
			throw new IllegalArgumentException("%s is not a valid identity no.".formatted(value));
		// object pooling
		var cached = TC_KIMLIKNO_CACHE.get(value);
		if (Objects.isNull(cached)) {
			cached = new TcKimlikNo(value);
			TC_KIMLIKNO_CACHE.put(value, cached);
		}
		return cached; // always return a valid TcKimlikNo object
	}

	public static boolean isValid(String value) {
		if (value == null)
			return false;
		if (!value.matches("^\\d{11}$")) { // fail-fast
			return false;
		}
		int[] digits = new int[11];
		for (int i = 0; i < digits.length; ++i) {
			digits[i] = value.charAt(i) - '0';
		}
		int x = digits[0];
		int y = digits[1];
		for (int i = 1; i < 5; i++) {
			x += digits[2 * i];
		}
		for (int i = 2; i <= 4; i++) {
			y += digits[2 * i - 1];
		}
		int c1 = 7 * x - y;
		if (c1 % 10 != digits[9]) {
			return false;
		}
		int c2 = 0;
		for (int i = 0; i < 10; ++i) {
			c2 += digits[i];
		}
		if (c2 % 10 != digits[10]) {
			return false;
		}
		return true;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TcKimlikNo other = (TcKimlikNo) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "TcKimlikNo [value=" + value + "]";
	}

}
