package com.bkahlert.idesupport.javadoc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Björn Kahlert
 */
class ConditionalJavaDocGenerationStrategy implements JavaDocGenerationStrategy {
    private final Function<String, Optional<JavaDocSubject>> classNameConverter;
    private final Function<JavaDocSubject, List<String>> javaDocGenerator;

    @SafeVarargs
    ConditionalJavaDocGenerationStrategy(
            Function<String, Optional<JavaDocSubject>> classNameConverter,
            Function<JavaDocSubject, String>... javaDocGenerators) {
        this.classNameConverter = classNameConverter;
        javaDocGenerator = subject -> Stream.of(javaDocGenerators).map(generator -> generator.apply(subject)).collect(Collectors.toList());
    }

    @Override
    public List<String> generateForClassName(String className) {
        return classNameConverter.apply(className).map(javaDocGenerator).orElse(Collections.emptyList());
    }
}
