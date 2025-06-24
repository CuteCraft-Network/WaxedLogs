plugins {
    id("java")
}

group = "net.cutecraft"
version = "1.0.0"

repositories {
    mavenCentral()
    maven(uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots"))
}

dependencies {
    compileOnly(libs.spigot)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks {
    jar {
        archiveBaseName.set(rootProject.name)
        archiveVersion.set(version.toString())
    }
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
}