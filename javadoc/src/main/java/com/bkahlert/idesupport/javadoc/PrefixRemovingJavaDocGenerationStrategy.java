package com.bkahlert.idesupport.javadoc;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author Björn Kahlert
 */
class PrefixRemovingJavaDocGenerationStrategy extends ConditionalJavaDocGenerationStrategy {
    @SafeVarargs
    public PrefixRemovingJavaDocGenerationStrategy(Collection<String> prefixes, Function<JavaDocSubject, String>... javaDocGenerators) {
        super(className -> prefixes.stream().filter(className::startsWith).findFirst()
                        .map(prefix -> new JavaDocSubject(className.substring(prefix.length()))),
                javaDocGenerators);
    }
}
