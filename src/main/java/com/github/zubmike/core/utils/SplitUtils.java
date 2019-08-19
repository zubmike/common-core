package com.github.zubmike.core.utils;

import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SplitUtils {

	public static final String DEFAULT_SPLITTER = ",";

	public static Set<Integer> splitInt(String values) {
		return splitInt(values, DEFAULT_SPLITTER);
	}

	public static Set<Integer> splitInt(String values, String regexSplit) {
		return splitNumber(values, regexSplit, Integer::valueOf);
	}
	public static Set<Long> splitLong(String values) {
		return splitLong(values, ",");
	}

	public static Set<Long> splitLong(String values, String regexSplit) {
		return splitNumber(values, regexSplit, Long::valueOf);
	}

	public static <T extends Number> Set<T> splitNumber(String values, String regexSplit, Function<String, T> valueOf) {
		try {
			return split(values, regexSplit, valueOf);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException();
		}
	}

	public static Set<String> splitString(String values) {
		return splitString(values, ",");
	}

	public static Set<String> splitString(String values, String regexSplit) {
		return split(values, regexSplit, value -> value);
	}

	public static <T> Set<T> split(@Null String values, String regexSplit, Function<String, T> valueOf) {
		if (values == null || values.isEmpty()) {
			return Collections.emptySet();
		}
		return Stream.of(values.split(regexSplit))
				.map(valueOf)
				.collect(Collectors.toSet());
	}

}
