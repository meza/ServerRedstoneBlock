import gg.meza.stonecuttermod.mod

plugins {
    id("gg.meza.stonecuttermod")
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
