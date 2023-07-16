package it.petshop.utility;

import java.util.Arrays;
import java.util.function.Consumer;

public class Param {
	public static <T> boolean isValid(T value, T... validValues) {
		return Arrays.stream(validValues).anyMatch(v -> (v == null ? value == null : v.equals(value)));
	}

	@SafeVarargs
	public static <T> void doOnMatch(T value, T[] values, Consumer<T>... actions) {
		if (values.length != (actions.length - 1)) {
			throw new IllegalArgumentException("Values and actions must have the same length");
		}

		for (int i = 0; i < values.length; i++) {
			if ((values[i] == null && value == null) || (values[i] != null && values[i].equals(value))) {
				actions[i].accept(value);
				return;
			}
		}

		actions[values.length].accept(value);
	}

}
