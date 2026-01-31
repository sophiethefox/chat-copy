plugins {
    id("gg.essential.loom") version "1.13.44" apply false
    id("gg.essential.multi-version.root")
}

preprocess {
    // 1.21.9 - 1.21.11
    val fabric12111 = createNode("1.21.11-fabric", 12111, "yarn")

    // 1.20.4 - 1.21.8
    val fabric12004 = createNode("1.20.4-fabric", 12004, "yarn")

    // 1.20.0 - 1.20.3
    val fabric12000 = createNode("1.20.0-fabric", 12000, "yarn")

    fabric12111.link(fabric12004)
    fabric12004.link(fabric12000)

}