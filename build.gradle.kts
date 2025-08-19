plugins {
    id("java")
    id("application")
}

group = "net.ansinn"
version = "1.0-SNAPSHOT"

val gitHash: Provider<String> = providers.exec {
    commandLine("git", "rev-parse", "--short", "HEAD")
}.standardOutput.asText.map(String::trim).orElse("nogit")

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(24)) }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation(platform("io.helidon:helidon-bom:3.2.0"))
    implementation("io.helidon.webserver:helidon-webserver")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Commit" to gitHash.get()
        )
    }
}

application {

}

tasks.test {
    useJUnitPlatform()
}