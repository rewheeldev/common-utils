import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
//    id("de.undercouch.download") version "5.2.0"
}

group = "com.gmail.rewheeldev"
version = "0.0.2"

repositories {
    mavenCentral()
    ivy {
        url = uri("https://github.com/")
        patternLayout {
            artifact ("[organization]/[module]/raw/main/[module]-[revision].jar")
            setM2compatible(false)
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))


    implementation ("rewheeldev:common-utils:0.0.1"){

    }

//    implementation(fileTree(mapOf("dir" to buildDir, "include" to listOf("*.jar", "*.aar"))))
//    implementation ("common-utils:0.0.1") {
//        artifact {
//            name = "common-utils"
//            extension = "jar"
//            type = "jar"
//            url = "https://github.com/rewheeldev/common-utils/raw/main/common-utils-0.0.1.jar"
//        }
//    }

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Implementation-Version"] = archiveVersion
    }
}

//task("downloadFile") {
//    doLast {
//        download.run {
//            src("https://github.com/rewheeldev/common-utils/raw/main/common-utils-0.0.1.jar")
//            dest(buildDir)
//            overwrite(true)
//        }
//    }
//}
