plugins {
    id("gg.essential.loom") version "1.13.44" apply false
    id("gg.essential.multi-version.root")
}

preprocess {
    // 1.21.9 - 1.21.11
    val fabric12111 = createNode("1.21.11-fabric", 12111, "yarn")

    // Works as far back as 1.20.0 all the way up to 1.21.8
    val fabric12100 = createNode("1.21.0-fabric", 12100, "yarn")

    fabric12111.link(fabric12100)
}