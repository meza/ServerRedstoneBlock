import gg.meza.stonecuttermod.*;

plugins {
    id("java")
    id("idea")
    id("gg.meza.stonecuttermod")
    id("me.modmuss50.mod-publish-plugin")
}

val minecraftVersion = stonecutter.current.version

val isBeta = "next" in version.toString()

val testserverDir = "../../run/testserver/${mod.loader}"
val testClientDir = "../../run/testclient/${mod.loader}"
val generatedResources = "src/main/generated"

base { archivesName.set("${mod.id}-${mod.loader}") }

if (mod.isFabric) {
    fabricApi {
        configureDataGeneration {
            /*? if >= 1.21.4 {*/
            client = true
            /*? }*/
            outputDirectory.set(project.file(generatedResources))
        }
    }
}

loom {
    accessWidenerPath = rootProject.file("src/main/resources/${mod.id}.accesswidener")

    if (isForge) {
        forge.convertAccessWideners = true
    }

    decompilers {
        get("vineflower").apply { options.put("mark-corresponding-synthetics", "1") }
    }

    runConfigs.all {
        isIdeConfigGenerated = true
        runDir = "../../run"
//        vmArgs("-Dmixin.debug.export=true")
        if (environment == "client") programArgs("--username=houseofmeza")
    }

    runs {
        if (isForge && stonecutter.eval(stonecutter.current.version, "<1.21.4")) {
            create("Datagen") {
                data()
                programArgs("--all", "--mod", mod.id)
                programArgs("--output", project.file(generatedResources).absolutePath)
                programArgs("--existing", rootProject.file("src/main/resources").absolutePath)
                property("forge.logging.console.level", "debug")
                property("forge.logging.markers", "REGISTRIES")
            }
        }
        if (mod.isNeoforge && false) {
            if (stonecutter.eval(stonecutter.current.version, ">=1.21.4")) {

                create("ServerDatagen") {
                    serverData()
                    programArgs("--mod", mod.id)
                    programArgs("--output", project.file(generatedResources).absolutePath)
                    property("neoforge.logging.console.level", "debug")
                    property("neoforge.logging.markers", "REGISTRIES")
                }
                create("CliteDatagen") {
                    clientData()
                    programArgs("--mod", mod.id)
                    programArgs("--output", project.file(generatedResources).absolutePath)
                    property("neoforge.logging.console.level", "debug")
                    property("neoforge.logging.markers", "REGISTRIES")
                }


            } else {
                create("Datagen") {
                    data()
                    programArgs("--all", "--mod", mod.id)
                    programArgs("--output", project.file(generatedResources).toString())
                    programArgs("--existing", rootProject.file("src/main/resources").toString())
                    property("neoforge.logging.console.level", "debug")
                    property("neoforge.logging.markers", "REGISTRIES")
                }
            }
        }

        create("gameTestClient") {
            client()
            runDir = testClientDir
            if (mod.isFabric) {
                vmArg("-Dfabric-api.gametest")
                vmArg("-Dfabric-api.gametest.report-file=${rootProject.file("build/junit.xml")}");
            }
            if (mod.isForge) {
                property("forge.enabledGameTestNamespaces", mod.id)
                property("forge.enableGameTest", "true")
            }
        }
        //if (!(isForge && stonecutter.eval(stonecutter.current.version, "=1.21.4"))) {
        if (!(mod.isForge)) {
            create("gameTestServer") {
                name("Game Test Server")
                server()
                runDir = testserverDir
                if (mod.isFabric) {
                    vmArg("-Dfabric-api.gametest")
                    vmArg("-Dfabric-api.gametest.report-file=${rootProject.file("build/junit.xml")}");
                }
                if (mod.isForge) {
                    property("forge.enabledGameTestNamespaces", mod.id)
                    property("forge.enableGameTest", "true")
                    property("forge.gameTestServer", "true")
                }

                if (mod.isNeoforge) {
                    property("neoforge.enabledGameTestNamespaces", mod.id)
                    property("neoforge.enableGameTest", "true")
                    property("neoforge.gameTestServer", "true")
                }
            }
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    if (!mod.isNeoforge) {
        mappings("net.fabricmc:yarn:${mod.prop("yarn_mappings")}:v2")
    } else {
        mappings(loom.layered {
            mappings("net.fabricmc:yarn:${mod.prop("yarn_mappings")}:v2")
            mappings("dev.architectury:yarn-mappings-patch-neoforge:${mod.prop("yarn_mappings_neoforge_patch")}")
        })
    }

    if (mod.isForge) {
        println("Adding Forge dependencies")
        "forge"("net.minecraftforge:forge:${mod.prop("forge_version")}")
    }
    if (mod.isFabric) {
        println("Adding Fabric dependencies")
        modImplementation("net.fabricmc:fabric-loader:${mod.prop("loader_version")}")
        modApi("net.fabricmc.fabric-api:fabric-api:${mod.prop("fabric_version")}")
        modApi("net.fabricmc.fabric-api:fabric-gametest-api-v1:${mod.prop("fabric_version")}")
    }

    if (mod.isNeoforge) {
        "neoForge"("net.neoforged:neoforge:${mod.prop("neoforge_version")}")
    }
}

val buildAndCollect = tasks.register<Copy>("buildAndCollect") {
    group = "build"
    from(tasks.remapJar.get().archiveFile)
    into(rootProject.layout.buildDirectory.file("libs"))
    dependsOn("build")
}

//if (isNeoforge && stonecutter.eval(stonecutter.current.version, ">=1.21.4")) {
//    tasks.register("runDatagen") {
//        dependsOn(project.tasks.named("runServerDatagen"), project.tasks.named("runClientDatagen"))
//    }
//}


if (stonecutter.current.isActive) {
    rootProject.tasks.register("buildActive") {
        group = "project"
        dependsOn(buildAndCollect)
    }
    rootProject.tasks.register("runActive") {
        group = "project"
        dependsOn(tasks.named("runClient"))
    }

    rootProject.tasks.register("dataGenActive") {
        group = "project"
        dependsOn(tasks.named("runDatagen"))
    }

    rootProject.tasks.register("testActiveClient") {
        group = "project"
        dependsOn(tasks.named("runGameTestClient"))
    }

    if (!(mod.isForge && minecraftVersion.equals("1.21.4"))) {
        rootProject.tasks.register("testActiveServer") {
            group = "project"
            dependsOn(tasks.named("runGameTestServer"))
        }
    }
}

tasks.register<ConfigureMinecraftClient>("configureMinecraft") {
    group = "project"

    additionalLines = mapOf(
        "joinedFirstServer" to "true",
        "pauseOnLostFocus" to "false",
        "lastServer" to "127.0.0.1",
        "soundDevice" to "OpenAL Soft on Game (TC-Helicon GoXLR)"
    )
}


tasks.named("runClient") {
    dependsOn(tasks.named("configureMinecraft"))
}

publishMods {
    changelog.set(
        rootProject.file("changelog.md").takeIf { it.exists() }?.readText() ?: "No changelog provided."
    )
    file = tasks.remapJar.get().archiveFile
    version = "${mod.version}+${mod.loader}-${minecraftVersion}"
    type.set(if (isBeta) BETA else STABLE)
    modLoaders.add(mod.loader)
    displayName = "${mod.version} for ${mod.loader.upperCaseFirst()} $minecraftVersion"

    modrinth {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN").orElse("")
        projectId = providers.environmentVariable("MODRINTH_ID").orElse("0")
        minecraftVersions.add(stonecutter.current.version)
        announcementTitle = "Download ${mod.version}+${mod.loader}-${minecraftVersion}from Modrinth"
        if (mod.isFabric) requires("fabric-api")
    }

    curseforge {
        projectSlug = providers.environmentVariable("CURSEFORGE_SLUG").orElse("server-redstone-block")
        projectId = providers.environmentVariable("CURSEFORGE_ID").orElse("0")
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN").orElse("")
        minecraftVersions.add(stonecutter.current.version)
        clientRequired = true
        serverRequired = false
        announcementTitle = "Download ${mod.version}+${mod.loader}-${minecraftVersion} from CurseForge"
        if (mod.isFabric) requires("fabric-api")
    }

    dryRun = providers.environmentVariable("DO_PUBLISH").getOrElse("true").toBoolean()
}
