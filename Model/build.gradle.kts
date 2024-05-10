plugins {
    kotlin("jvm")
}

group = "edu.kdmk.cipher.implementation"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.9")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.13")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation(kotlin("reflect"))
    // https://mvnrepository.com/artifact/jakarta.xml.bind/jakarta.xml.bind-api
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
    // https://mvnrepository.com/artifact/commons-io/commons-io
    implementation("commons-io:commons-io:2.16.1")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
