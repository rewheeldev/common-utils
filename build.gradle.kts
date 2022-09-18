import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("de.undercouch.download") version "5.2.0"
}

group = "com.gmail.rewheeldev"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    //
    implementation(fileTree(mapOf("dir" to buildDir, "include" to listOf("*.jar", "*.aar"))))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
//    archiveFileName.set("rewheeldev.jar")
    manifest {
        attributes["Implementation-Version"] = archiveVersion
    }
//    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

task("downloadFile") {
    doLast {
        download.run {
            src("https://github.com/rewheeldev/common-utils/raw/main/common-utils-0.0.1.jar")
            dest(buildDir)
            overwrite(true)
        }
    }
}
