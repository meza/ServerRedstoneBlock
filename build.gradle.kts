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

if (!mod.isNeoforge || stonecutter.eval(stonecutter.current.version, "<1.21.5")) {
    tasks.named("processResources") {
        doFirst {
            (this as ProcessResources).exclude("**/test_instance/**", "**/test_environment/**")
        }
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
