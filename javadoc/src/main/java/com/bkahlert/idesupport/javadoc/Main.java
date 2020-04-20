package com.bkahlert.idesupport.javadoc;

import java.util.Arrays;

/**
 * Main entry point for the JavaDoc library.
 *
 * @author Björn Kahlert
 */
public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException(
                    "Only one argument (in IntelliJ: \"className()\") is allowed." + System.lineSeparator() +
                            "Provided: " + Arrays.asList(args) + System.lineSeparator() +
                            "Full example: groovyScript(\"/Users/bkahlert/Development/com.bkahlert/ide-support/src/main/groovy/javadoc.groovy\", className())");
        }
        System.out.println(new JavaDocGenerator().generateDescriptionForClassName(args[0]));
    }
}
