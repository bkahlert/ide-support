package com.bkahlert.idesupport.javadoc;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * Tests for {@link JavaDocGenerator}.
 *
 * @author Björn Kahlert
 */
class JavaDocGeneratorTest {

    static final String EMPTY_LINE = "";

    final JavaDocGenerator generator = new JavaDocGenerator();

    private static String[] forClasses(String... classes) {
        return classes;
    }

    private static SimpleImmutableEntry<String[], String> description(
            String description, String[] classNames) {
        return new SimpleImmutableEntry<>(classNames, description);
    }

    @SafeVarargs
    private static <T> Stream<T> generate(T... classes) {
        return Stream.of(classes);
    }

    private static String startingWith(String... with) {
        return Stream.of(with).collect(Collectors.joining(System.lineSeparator()));
    }

    private DynamicNode createTests(Map.Entry<String[], String> testEntry) {
        return dynamicContainer("start with \"" + testEntry.getValue() + "\"",
                Stream.of(testEntry.getKey()).map(className -> dynamicTest("for " + className,
                        () -> assertThat(generator.generateDescriptionForClassName(className)).startsWith(testEntry.getValue()))
                ));
    }

    @SuppressWarnings({"DuplicateStringLiteralInspection", "JUnitTestMethodWithNoAssertions"})
    @TestFactory
    Stream<DynamicNode> should() {
        return generate(
                description(startingWith("Indicator for some class's health."),
                        forClasses("SomeClassHealthIndicator")),
                description(startingWith("Default implementation of {@link SomeClass}."),
                        forClasses("SomeClassImpl")),
                description(startingWith("Mock implementation of {@link SomeClass}."),
                        forClasses("MockSomeClass", "SomeClassMock")),
                description(startingWith("Integration tests for {@link My}."),
                        forClasses("MyIT", "MyIntegrationTest", "MyIntegrationTests", "MyIntegrationsTest", "MyIntegrationsTests")),
                description(startingWith("Tests for {@link My}."),
                        forClasses("MyTest", "MyTests")),
                description(startingWith("Helper methods that help dealing with {@link Class}."),
                        forClasses("ClassHelper", "ClassHelpers", "HelperClass", "HelpersClass")),
                description(startingWith("Utils that make it easier to conduct {@link Class} related / involving tests."),
                        forClasses("ClassTestUtility", "ClassTestUtilities", "TestUtilityClass", "TestUtilitiesClass", "ClassTestUtil", "ClassTestUtils",
                                "TestUtilClass", "TestUtilsClass")),
                description(startingWith("Utility methods that help dealing with {@link Class}."),
                        forClasses("ClassUtility", "ClassUtilities", "UtilityClass", "UtilitiesClass", "ClassUtil", "ClassUtils", "UtilClass", "UtilsClass")),
                description(startingWith(
                        "Low-level utility methods targeted mostly at library writers to avoid having to re-implement already existing class related " +
                                "functionality."),
                        forClasses("ClassSupport", "SupportClass")),
                description(startingWith(
                        "AssertJ entry point for custom assertions related to {@link Class classes}.",
                        EMPTY_LINE,
                        "Simply create a {@code static import} for {@link ClassAssertions#assertThat(Class)} and start asserting."),
                        forClasses("ClassAssertions")),
                description(startingWith("AssertJ {@link org.assertj.core.api.Assert} that provides custom assertion options for {@link Class classes}."),
                        forClasses("ClassAssert")),
                description(startingWith("Test configuration for Spring Boot integration tests."),
                        forClasses("ClassTestConfiguration")),
                description(startingWith(
                        "Binds to the corresponding properties and provides the {@link ConfigurationClass}",
                        "with these data to allow for proper configuration."),
                        forClasses("ClassConfigurationProperties", "ClassProperties")),
                description(startingWith("Configuration for the class feature."),
                        forClasses("ClassConfiguration")),
                description(
                        startingWith("A {@link org.springframework.core.io.support.PropertySourceFactory} capable of generating {@link ClassPropertySource}s."),
                        forClasses("ClassPropertySourceFactory")),
                description(startingWith("A {@link org.springframework.core.env.PropertySource} that describes {@link Class classes}."),
                        forClasses("ClassPropertySource")),
                description(startingWith("A controller that provides class relevant REST endpoints."),
                        forClasses("ClassController"))
        ).map(this::createTests);
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "ImplSomeClass",
            "MyFabulousITs",
            "MyFabulousTestss",
            "AssertionsMyFabulous",
            "AssertMyFabulous",
            "TestConfigurationMyFabulous",
            "ConfigurationPropertiesMyFabulous",
            "PropertiesMyFabulous",
            "ConfigurationMyFabulous",
            "PropertySourceFactoryMyFabulous",
            "PropertySourceMyFabulous",
            "ControllerMyFabulous"
    })
    void should_generate_default_description_for_false_friends(String falseFriendClassName) {
        assertThat(generator.generateDescriptionForClassName(falseFriendClassName)).hasLineCount(7).startsWith("com.bkahlert.idesupport.javadoc.Main entry point");
    }

    @Test
    void should_generate_default_description() {
        assertThat(generator.generateDescriptionForClassName("MyFabulousClass")).hasLineCount(7)
                .contains("com.bkahlert.idesupport.javadoc.Main entry point", "Instances", "{@link MyFabulousClass}", "my fabulous class");
    }
}
