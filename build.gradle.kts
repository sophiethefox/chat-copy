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
}

dependencies {
    val fabricApiVersion = dependencyVersion("fabric-api", "Fabric API")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
    modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:1.2.2")
}

tasks {
    jar {
        from(rootProject.file("LICENSE")) {
            rename { "minecraft-heads-browser_${it}" }
        }
    }
}