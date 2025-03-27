import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
    id("me.modmuss50.mod-publish-plugin")
}

modSettings {
    clientOptions {
        fov = 90
        guiScale = 3
        narrator = false
        darkBackground = true
        musicVolume = 0.0
    }
}

publishMods {
    modrinth {
        if (mod.isFabric) requires("fabric-api")
    }

    curseforge {
        clientRequired = true
        serverRequired = false
        if (mod.isFabric) requires("fabric-api")
    }
}

//tasks.named("runClient") {
//    logger.error("show this error")
//    throw GradleException("Task execution disrupted and exited.")
//}
