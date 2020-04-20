package com.bkahlert.idesupport.javadoc;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author Björn Kahlert
 */
class InfixRemovingJavaDocGenerationStrategy extends ConditionalJavaDocGenerationStrategy {
    @SafeVarargs
    public InfixRemovingJavaDocGenerationStrategy(Collection<String> infixes, Function<JavaDocSubject, String>... javaDocGenerators) {
        super(className -> infixes.stream().filter(className::contains).findFirst()
                        .map(infix -> new JavaDocSubject(className.replace(infix, ""))),
                javaDocGenerators);
    }
}
