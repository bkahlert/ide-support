package com.bkahlert.idesupport.javadoc;

import java.util.List;

/**
 * @author Björn Kahlert
 */
@FunctionalInterface
interface JavaDocGenerationStrategy {
    List<String> generateForClassName(String className);
}
