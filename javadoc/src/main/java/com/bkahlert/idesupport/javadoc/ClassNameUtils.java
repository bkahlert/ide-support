package com.bkahlert.idesupport.javadoc;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum ClassNameUtils {
    ;
    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");

    public static List<String> splitIntoParts(CharSequence className) {
        return Arrays.asList(CAMEL_CASE_PATTERN.split(className));
    }

    public static String fromParts(Stream<String> parts) {
        return parts.map(ClassNameUtils::capitalize).collect(Collectors.joining());
    }

    public static String fromParts(List<String> parts) {
        return fromParts(parts.stream());
    }

    public static String fromParts(String[] parts) {
        return fromParts(Stream.of(parts));
    }

    private static String capitalize(String word) {
        assert !word.isEmpty();
        return word.substring(0, 1).toUpperCase(Locale.ENGLISH) + word.substring(1);
    }
}
