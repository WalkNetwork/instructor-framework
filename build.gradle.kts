plugins {
	kotlin("jvm") version "1.6.0"
	id("java")
	id("com.github.johnrengelman.shadow") version "6.0.0"
	id("maven-publish")
	id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
	id("signing")
}

group = "io.github.uinnn"
version = "1.4.2"

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	compileOnly("io.github.uinnn:walk-server:1.9.0")
	compileOnly(kotlin("stdlib-jdk8"))
}

nexusPublishing {
	repositories {
		sonatype {
			nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
			snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
		}
	}
}

tasks {
	publishing {
		repositories {
			maven {
				url = uri("https://repo.maven.apache.org/maven2/")
			}
		}
		
		publications {
			create<MavenPublication>("maven") {
				from(project.components["kotlin"])
				groupId = project.group.toString()
				artifactId = "instructor-framework"
				version = project.version.toString()
				
				val sourcesJar by creating(Jar::class) {
					archiveClassifier.set("sources")
					from(sourceSets.main.get().allSource)
				}
				
				val javadocJar by creating(Jar::class) {
					dependsOn.add(javadoc)
					archiveClassifier.set("javadoc")
					from(javadoc)
				}
				
				val javaJar = this@tasks.jar
				
				setArtifacts(listOf(sourcesJar, javadocJar, javaJar))
				
				pom {
					name.set("instructor-framework")
					description.set("A useful very simple way to create instructors (commands) in spigot with kotlin.")
					url.set("https://github.com/uinnn/instructor-framework")
					developers {
						developer {
							id.set("uinnn")
							name.set("Uin Carrara")
							email.set("uin.carrara@gmail.com")
						}
					}
					licenses {
						license {
							name.set("MIT Licenses")
						}
					}
					scm {
						url.set("https://github.com/uinnn/instructor-framework/tree/master/src")
					}
				}
			}
		}
	}
	
	signing {
		sign(publishing.publications["maven"])
	}
	
	compileKotlin {
		kotlinOptions.freeCompilerArgs +=
			"-Xopt-in=kotlin.ExperimentalStdlibApi," +
				"kotlinx.serialization.ExperimentalSerializationApi," +
				"kotlinx.serialization.InternalSerializationApi"
	}
	
	shadowJar {
		destinationDir = file(project.property("folder")!!)
		archiveName = "${project.name}.jar"
	}
}
