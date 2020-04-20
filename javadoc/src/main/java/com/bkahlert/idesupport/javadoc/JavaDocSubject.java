package com.bkahlert.idesupport.javadoc;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * @author Björn Kahlert
 */
public class JavaDocSubject {
    private final List<String> classNameParts;

    public JavaDocSubject(CharSequence className) {
        classNameParts = ClassNameUtils.splitIntoParts(className);
    }

    public JavaDocSubject(Stream<String> parts) {
        this(ClassNameUtils.fromParts(parts));
    }

    public JavaDocSubject(List<String> parts) {
        this(ClassNameUtils.fromParts(parts));
    }

    public String asClassName() {
        return String.join("", classNameParts);
    }

    public String asLinkedClass() {
        return "{@link " + asClassName() + "}";
    }

    public String asLinkedPluralClass() {
        return "{@link " + asClassName() + " " + toPlural().asSpokenName() + "}";
    }

    public String asSpokenName() {
        return String.join(" ", classNameParts).toLowerCase(Locale.ENGLISH);
    }

    /**
     * Returns the first {@code n} parts as spoken words.
     *
     * @param numberOfParts number of parts to be used starting to count from the left.
     *                      negative numbers are the number of parts removed from the right
     * @return
     */
    public String asSpokenName(int numberOfParts) {
        return (numberOfParts >= 0 ? removePrefix(numberOfParts) : removeSuffix(-numberOfParts)).asSpokenName();
    }

    public JavaDocSubject toPlural() {
        String spokenPlural = Plurals.pluralize(asSpokenName());
        String[] split = spokenPlural.split(" ");
        return new JavaDocSubject(String.join("", split));
    }

    public JavaDocSubject removePrefix() {
        return new JavaDocSubject(classNameParts.subList(1, classNameParts.size()));
    }

    public JavaDocSubject removePrefix(int count) {
        JavaDocSubject result = this;
        for (int i = 0; i < count; i++) {
            result = result.removePrefix();
        }
        return result;
    }

    public JavaDocSubject removeInfix(String infix) {
        return new JavaDocSubject(classNameParts.stream().filter(part -> !part.equals(infix)));
    }

    public JavaDocSubject removeSuffix() {
        return new JavaDocSubject(classNameParts.subList(0, classNameParts.size() - 1));
    }

    public JavaDocSubject removeSuffix(int count) {
        JavaDocSubject result = this;
        for (int i = 0; i < count; i++) {
            result = result.removeSuffix();
        }
        return result;
    }

    public JavaDocSubject keepSuffix() {
        return new JavaDocSubject(classNameParts.subList(classNameParts.size() - 1, classNameParts.size()));
    }
}
