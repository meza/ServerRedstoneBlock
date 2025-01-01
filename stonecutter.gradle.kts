plugins {
    id("dev.kikugie.stonecutter")
    id("dev.architectury.loom") version "1.9.+" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.8.+" apply false
}
stonecutter active "1.21.4-fabric" /* [SC] DO NOT EDIT */
stonecutter.automaticPlatformConstants = true

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("build")
}

stonecutter registerChiseled tasks.register("chiseledTest", stonecutter.chiseled) {
    group = "project"
    ofTask("test")
}

stonecutter registerChiseled tasks.register("chiseledTestE2E", stonecutter.chiseled) {
    group = "project"
    ofTask("runGameTestServer")
}

stonecutter registerChiseled tasks.register("chiseledBuildAndCollect", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
}

stonecutter registerChiseled tasks.register("chiseledPublishMods", stonecutter.chiseled) {
    group = "project"
    ofTask("publishMods")
}
