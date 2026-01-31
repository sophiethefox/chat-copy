plugins {
    id("gg.essential.loom") version "1.13.44" apply false
    id("gg.essential.multi-version.root")
}

preprocess {
    // 1.21.11+
    // handleClickEvent in ChatScreen.class introduced
    val fabric12111 = createNode("1.21.11-fabric", 12111, "yarn")

    // 1.21.09 - 1.21.10
    // mouseClicked in ChatScreen.class method signature changed
    val fabric12109 = createNode("1.21.09-fabric", 12109, "yarn")

    // 1.20.4 - 1.21.8
    // renderBackground in Screen.class introduced
    val fabric12004 = createNode("1.20.4-fabric", 12004, "yarn")

    // 1.20.0 - 1.20.3
    val fabric12000 = createNode("1.20.0-fabric", 12000, "yarn")

    fabric12111.link(fabric12109)
    fabric12109.link(fabric12004)
    fabric12004.link(fabric12000)

    // https://github.com/EssentialGG/essential-gradle-toolkit/blob/6f3a53d690e7f3c489121dfbc7d1e75b1e0278db/src/main/kotlin/gg/essential/defaults/loom.gradle.kts
    // https://maven.fabricmc.net/docs/yarn-1.21.8+build.1/net/minecraft/client/gui/screen/ChatScreen.html
}