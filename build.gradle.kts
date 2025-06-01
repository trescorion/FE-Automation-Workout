plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.seleniumhq.selenium:selenium-java:4.20.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.8.0")
    implementation("commons-io:commons-io:2.11.0")

}

tasks.test {
    useJUnitPlatform()
}