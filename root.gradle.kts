plugins {
    id("gg.essential.loom") version "1.13.44" apply false
    id("gg.essential.multi-version.root")
}

preprocess {
    val fabric12111 = createNode("1.21.11-fabric", 12111, "yarn")
    val fabric12100 = createNode("1.21.0-fabric", 12100, "yarn")
    fabric12111.link(fabric12100)
}