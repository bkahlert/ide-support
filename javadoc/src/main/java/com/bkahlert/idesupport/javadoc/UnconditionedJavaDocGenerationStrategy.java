package com.bkahlert.idesupport.javadoc;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Björn Kahlert
 */
class UnconditionedJavaDocGenerationStrategy implements JavaDocGenerationStrategy {
    private final Function<JavaDocSubject, List<String>> javaDocGenerator;

    public UnconditionedJavaDocGenerationStrategy(Function<JavaDocSubject, String>... javaDocGenerators) {
        javaDocGenerator = subject -> Stream.of(javaDocGenerators).map(generator -> generator.apply(subject)).collect(Collectors.toList());
    }

    @Override
    public List<String> generateForClassName(String className) {
        return javaDocGenerator.apply(new JavaDocSubject(className));
    }
}
