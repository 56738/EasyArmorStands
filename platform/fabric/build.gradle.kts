plugins {
    id("easyarmorstands.base")
    id("easyarmorstands.publish")
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.resource.factory.fabric.convention)
}

dependencies {
    api(project(":easyarmorstands-platform-modded"))
    api(libs.fabric.api)
    api(libs.fabric.loader)
    api(libs.fabric.permissions.api)
    api(libs.adventure.platform.fabric)
    minecraft(libs.minecraft)
}

loom {
    accessWidenerPath = file("src/main/resources/easyarmorstands-platform-fabric.classtweaker")
}

fabricModJson {
    id = "easyarmorstands-platform-fabric"
    accessWidener = "easyarmorstands-platform-fabric.classtweaker"
    depends("adventure-platform-fabric", "*")
    depends("fabric-api", "*")
    depends("fabric-permissions-api-v0", "*")
    depends("fabricloader", ">=" + libs.versions.fabric.loader.get())
    depends("java", ">=" + java.toolchain.languageVersion.get().asInt())
    depends("minecraft", "~" + libs.versions.minecraft.get())
}
