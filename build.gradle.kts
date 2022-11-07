import java.io.File
import java.io.FileInputStream
import java.util.Properties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.flywaydb.flyway") version "9.3.1"
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    kotlin("jvm") version "1.5.0"
    kotlin("kapt") version "1.7.0"
    kotlin("plugin.spring") version "1.5.0"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.20"
    application
    distribution
}

group = "io.billie"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11


val dbConf = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "database.env")))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.11")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.11")

    implementation("org.flywaydb:flyway-core")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.4")

    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.17.5")
    testImplementation("org.testcontainers:junit-jupiter:1.17.5")
}

flyway {
    url = dbConf.getProperty("DATABASE_URL")
    user = dbConf.getProperty("POSTGRES_USER")
    password = dbConf.getProperty("POSTGRES_PASSWORD")
    locations = arrayOf(dbConf.getProperty("DATABASE_MIGRATION"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

noArg {
    annotation("javax.persistence.Entity")
}
