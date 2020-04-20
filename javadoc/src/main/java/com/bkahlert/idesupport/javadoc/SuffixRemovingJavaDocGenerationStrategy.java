package com.bkahlert.idesupport.javadoc;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author Björn Kahlert
 */
class SuffixRemovingJavaDocGenerationStrategy extends ConditionalJavaDocGenerationStrategy {
    @SafeVarargs
    public SuffixRemovingJavaDocGenerationStrategy(Collection<String> suffixes, Function<JavaDocSubject, String>... javaDocGenerators) {
        super(className -> suffixes.stream().filter(className::endsWith).findFirst()
                        .map(suffix -> new JavaDocSubject(className.substring(0, className.length() - suffix.length()))),
                javaDocGenerators);
    }
}
