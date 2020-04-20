package com.bkahlert.idesupport.javadoc;


import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JavaDocGenerator {

    List<JavaDocGenerationStrategy> strategies;

    public JavaDocGenerator() {
        strategies = new ArrayList<>();
        forSuffixes("Indicator",
                subject -> "Indicator for " + subject.asSpokenName(-1) + "'s " + subject.keepSuffix().asSpokenName() + ".");
        forSuffixes("Exception",
                subject -> "Exception that is thrown in case a problem with " + subject.asSpokenName() + " occurred.");
        forSuffixes("Impl",
                subject -> "Default implementation of " + subject.asLinkedClass() + ".");
        forInfixes("Mock",
                subject -> "Mock implementation of " + subject.asLinkedClass() + ".");
        forSuffixes(asList("IT", "IntegrationTest", "IntegrationTests", "IntegrationsTest", "IntegrationsTests"),
                subject -> "Integration tests for " + subject.asLinkedClass() + ".");
        forSuffixes(asList("Tests", "Test"),
                subject -> "Tests for " + subject.asLinkedClass() + ".");
        forInfixes(asList("Helpers", "Helper"),
                subject -> "Helper methods that help dealing with " + subject.asLinkedClass() + ".");
        forInfixes(asList("TestUtilities", "TestUtility", "TestUtils", "TestUtil"),
                subject -> "Utils that make it easier to conduct " + subject.asLinkedClass() + " related / involving tests.");
        forInfixes(asList("Utilities", "Utility", "Utils", "Util"),
                subject -> "Utility methods that help dealing with " + subject.asLinkedClass() + ".");
        forInfixes(asList("Support"),
                subject -> "Low-level utility methods targeted mostly at library writers to avoid having to re-implement already existing " +
                        subject.asSpokenName() + " related functionality.");
        forInfixes(asList("Fixtures", "Fixture"),
                subject -> "Fixtures that can be used as test data.\n" +
                        "<p>\n" +
                        "Test writers are encouraged to reuse already existing test data instead\n" +
                        "of recreating the same or similar " + subject.asLinkedClass() + " instances all over again.\n" +
                        "Please feel free to extend this class with further test.");
        forSuffixes(asList("Assertions"),
                subject -> "AssertJ entry point for custom assertions related to " + subject.asLinkedPluralClass() + ".\n" +
                        "\n" +
                        "Simply create a {@code static import} for {@link " + subject.asClassName() + "Assertions#assertThat(" + subject.asClassName() +
                        ")} and start asserting.");
        forSuffixes(asList("Assert"),
                subject -> "AssertJ {@link org.assertj.core.api.Assert} that provides custom assertion options for " + subject.asLinkedPluralClass() + ".");
        forSuffixes(asList("TestConfiguration"),
                subject -> "Test configuration for Spring Boot integration tests.");
        forSuffixes(asList("ConfigurationProperties", "Properties"),
                subject -> "Binds to the corresponding properties and provides the {@link Configuration" + subject.asClassName() + "}\n" +
                        "with these data to allow for proper configuration.");
        forSuffixes(asList("Configuration"),
                subject -> "Configuration for the " + subject.asSpokenName() + " feature.");
        forSuffixes(asList("PropertySourceFactory"),
                subject -> "A {@link org.springframework.core.io.support.PropertySourceFactory} capable of generating" +
                        " {@link " + subject.asClassName() + "PropertySource}s.",
                subject -> "A {@link org.springframework.core.io.support.PropertySourceFactory} capable of generating" +
                        " " + subject.asSpokenName() + " {@link PropertySource}s.");
        forSuffixes(asList("PropertySource"),
                subject -> "A {@link org.springframework.core.env.PropertySource} that describes " + subject.asLinkedPluralClass() + ".");
        forSuffixes(asList("Controller"),
                subject -> "A controller that provides " + subject.asSpokenName() + " relevant REST endpoints.");
        strategies.add(new UnconditionedJavaDocGenerationStrategy(
                subject -> "com.bkahlert.idesupport.javadoc.Main entry point for " + subject.asSpokenName() + "s that allows to ",
                subject -> "Instances of this class ",
                subject -> subject.asClassName() + " allows to ",
                subject -> subject.asClassName() + " provides functionality to easily ",
                subject -> subject.asClassName() + " provides functionality to ",
                subject -> subject.asClassName() + " is a " + subject.asLinkedClass(),
                subject -> subject.asClassName() + " is a " + subject.asSpokenName()
        ));
    }

    void forPrefixes(List<String> prefixes, Function<JavaDocSubject, String>... javaDocGenerators) {
        strategies.add(new PrefixRemovingJavaDocGenerationStrategy(prefixes, javaDocGenerators));
    }

    void forPrefixes(String prefix, Function<JavaDocSubject, String>... javaDocGenerators) {
        strategies.add(new PrefixRemovingJavaDocGenerationStrategy(Collections.singleton(prefix), javaDocGenerators));
    }

    void forInfixes(String infix, Function<JavaDocSubject, String>... javaDocGenerators) {
        strategies.add(new InfixRemovingJavaDocGenerationStrategy(Collections.singleton(infix), javaDocGenerators));
    }

    void forInfixes(List<String> infixes, Function<JavaDocSubject, String>... javaDocGenerators) {
        strategies.add(new InfixRemovingJavaDocGenerationStrategy(infixes, javaDocGenerators));
    }

    void forSuffixes(List<String> suffixes, Function<JavaDocSubject, String>... javaDocGenerators) {
        strategies.add(new SuffixRemovingJavaDocGenerationStrategy(suffixes, javaDocGenerators));
    }

    void forSuffixes(String suffix, Function<JavaDocSubject, String>... javaDocGenerators) {
        strategies.add(new SuffixRemovingJavaDocGenerationStrategy(Collections.singleton(suffix), javaDocGenerators));
    }

    public String generateDescriptionForClassName(String className) {
        return strategies.stream()
                .flatMap(strategy -> strategy.generateForClassName(className).stream())
                .filter(lines -> !lines.isEmpty())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
