plugins {
    `java-library`
    id("io.freefair.lombok") version "5.0.0"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_13
}

group = "com.bkahlert.idesupport"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback", "logback-classic", "1.2.3")

    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.2")
    testImplementation("org.assertj", "assertj-core", "3.11.1")
}

tasks {
    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version,
                    "Main-Class" to "com.bkahlert.idesupport.javadoc.Main"
                )
            )
        }
    }
}
