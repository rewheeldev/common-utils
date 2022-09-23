import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.7.10"
    `maven-publish`
    signing
}

java {
    withJavadocJar()
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

group = "io.gmail.rewheeldev"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src/main/kotlin"))
        }
    }
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


val publicationName = "mavenKotlin"

publishing {
    publications {
        create<MavenPublication>(publicationName) {

            System.out.println("name: $name")

            artifactId = "common-utils"
            from(components["java"])
            groupId = "io.github.rewheeldev"
            version = "0.0.1"

            pom {
                name.set("common-utils")
                packaging = "jar"
                description.set("Common utils that can be used in java and kotlin projects")
                inceptionYear.set("2022")
                url.set("https://github.com/rewheeldev/common-utils")
                licenses {
                    license {
                        name.set("GNU General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                    }
                }
                developers {
                    developer {
                        id.set("Jere1831006")
                        name.set("Nickolas Ivantsov")
                        email.set("jere1831006@gmail.com")
                        organization.set("github.com.rewheeldev")
                        organizationUrl.set("https://github.com/rewheeldev")
                    }
                    developer {
                        id.set("avanchik")
                        name.set("Ruslan Onishchuk")
                        email.set("ruslan.onishchuk.90@gmail.com")
                        organization.set("github.com.rewheeldev")
                        organizationUrl.set("https://github.com/rewheeldev")
                    }
                }
//                https://github.com/rewheeldev/common-utils.git
                scm {
                    connection.set("scm:git:git://github.com/rewheeldev/common-utils.git")
                    developerConnection.set("scm:git:ssh://github.com:rewheeldev/common-utils.git")
                    url.set("https://github.com/rewheeldev/common-utils")
                }
            }
        }
        repositories {
            maven {
                name = "OSSRH"
                url = uri("https://s01.oss.sonatype.org/content/groups/staging/deploy/maven2")
                credentials {
                    username = project.findProperty("ossrhUserName").toString()
                    password = project.findProperty("ossrhPassword").toString()
                    System.out.println("username: $username")
                    System.out.println("password: $password")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications[publicationName])
}


tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}