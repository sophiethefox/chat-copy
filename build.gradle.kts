plugins {
    id("gg.essential.defaults")
    id("gg.essential.multi-version")
}

fun Project.dependencyVersion(name: String, friendlyName: String = name, defaultValue: String? = null): String {
    return this.findProperty("dependency.$name.version") as? String ?: defaultValue
    ?: error("No $friendlyName version defined for ${platform.mcVersionStr} (${platform.loaderStr})")
}

version = "1.0.0"
group = "cc.sophiethefox.chatcopy"
base.archivesName = "chat-copy"

repositories {
    maven { url = uri("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") }
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
    maven {
        name = "Nucleoid"
        url = uri("https://maven.nucleoid.xyz/")
    }
}

dependencies {
    val fabricApiVersion = dependencyVersion("fabric-api", "Fabric API")
    project.logger.info(fabricApiVersion)

    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")

    if (platform.mcVersion > 12111) {
        implementation("com.terraformersmc:modmenu:18.0.0-alpha.8") {
            exclude("eu.pb4")
        }
    } else if (platform.mcVersion == 12111) {
        modImplementation("com.terraformersmc:modmenu:17.0.0")
    } else if (platform.mcVersion == 12109) {
        modImplementation("com.terraformersmc:modmenu:16.0.0")
    } else if (platform.mcVersion == 12004) {
        modImplementation("com.terraformersmc:modmenu:9.2.0")
    } else if (platform.mcVersion == 12000) {
        modImplementation("com.terraformersmc:modmenu:7.0.1")
    }
    modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:1.2.2")
}

tasks {
    jar {
        from(rootProject.file("LICENSE")) {
            rename { "minecraft-heads-browser_${it}" }
        }
    }
}


java {
    withSourcesJar()
    if (platform.mcVersion > 12111) {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    } else {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}