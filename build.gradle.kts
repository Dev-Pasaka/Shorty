import io.ktor.plugin.features.*

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.24"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.presentation.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

tasks {
    create("stage").dependsOn("installDist")
}

ktor {
    fatJar {
        archiveFileName.set("Shorty.jar")
    }
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName.set("shorty-docker-image")
        this.imageTag.set("Shorty")
        imageTag.set("0.0.1-preview")
        portMappings.set(
            listOf(
                DockerPortMapping(
                    8088,
                    8088,
                    DockerPortMappingProtocol.TCP
                )
            )
        )


    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation("io.github.flaxoos:ktor-server-kafka-jvm:1.+")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")



    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-swagger-jvm")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")


    //Env config
    implementation("io.github.cdimascio:dotenv-java:2.2.0")
    //Encryption
    implementation("org.mindrot:jbcrypt:0.4")
    //KtorClient
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    //MongoDB
    implementation("org.mongodb:mongodb-driver-kotlin-sync:5.0.0")
    //CORS
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    //Resend
    implementation("com.resend:resend-java:3.1.0")
    //KtorClient
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    //Africastalking
    implementation("com.github.AfricasTalkingLtd.africastalking-java:core:3.4.11")
    // Redis
    implementation("redis.clients:jedis:5.1.2")

    implementation("com.maxmind.geoip2:geoip2:4.0.1")
    implementation("eu.bitwalker:UserAgentUtils:1.21")


}
