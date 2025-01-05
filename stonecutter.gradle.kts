plugins {
    id("dev.kikugie.stonecutter")
    id("dev.architectury.loom") version "1.9.+" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.8.+" apply false
}

stonecutter active "1.21.4-fabric" /* [SC] DO NOT EDIT */
stonecutter.automaticPlatformConstants = true

//stonecutter.tree.nodes.forEach {
//    val loader = it.metadata.project.substringAfterLast("-")
//    it.ext["loom.platform"] = loader
//}

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    description = "Builds all the versions for all the loaders"
    ofTask("build")
}

stonecutter registerChiseled tasks.register("chiseledClean", stonecutter.chiseled) {
    group = "project"
    description = "Cleans all the versions for all the loaders"
    ofTask("clean")
}

stonecutter registerChiseled tasks.register("chiseledDatagen", stonecutter.chiseled) {
    group = "project"
    description = "Runs data generators for all the versions for all the loaders"
    ofTask("runDatagen")
}

stonecutter registerChiseled tasks.register("chiseledTest", stonecutter.chiseled) {
    group = "project"
    description = "Runs tests for all the versions for all the loaders"
    ofTask("test")
}

stonecutter registerChiseled tasks.register("chiseledTestE2E", stonecutter.chiseled) {
    group = "project"
    description = "Runs end-to-end tests for all the versions for all the loaders"
    ofTask("runGameTestServer")
}

stonecutter registerChiseled tasks.register("chiseledBuildAndCollect", stonecutter.chiseled) {
    group = "project"
    description = "Builds all the versions for all the loaders and collects the outputs into the build directory of the root project"
    ofTask("buildAndCollect")
}

stonecutter registerChiseled tasks.register("chiseledPublishMods", stonecutter.chiseled) {
    group = "project"

    ofTask("publishMods")
}
