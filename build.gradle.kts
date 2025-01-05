import java.util.*

fun String.upperCaseFirst() = replaceFirstChar { if (it.isLowerCase()) it.uppercaseChar() else it }

plugins {
    id("idea")
    id("me.modmuss50.mod-publish-plugin")
    id("dev.architectury.loom")
}

val loader = project.name.substringAfterLast("-").lowercase()
project.ext["loom.platform"] = loader


fun getResourceVersionFor(version: String): Int {
    return when (version) {
        "1.20.2" -> 18
        "1.21" -> 34
        "1.21.4" -> 46
        else -> 18
    }
}

val minecraftVersion = stonecutter.current.version
val customPropsFile = rootProject.file("versions/dependencies/${minecraftVersion}.properties")

if (customPropsFile.exists()) {
    val customProps = Properties().apply {
        customPropsFile.inputStream().use { load(it) }
    }
    customProps.forEach { key, value ->
        project.extra[key.toString()] = value
    }
}

class ModData {
    val id: String get() = property("mod.id").toString()
    val name: String get() = property("mod.name").toString()
    val description: String get() = property("mod.description").toString()
    val version: String get() = property("mod.version").toString()
    val group: String get() = property("mod.group").toString()
    fun prop(key: String) = project.extra[key].toString()
}

val mod = ModData()
val isFabric = loader == "fabric"
val isForge = loader == "forge"
val isNeoforge = loader == "neoforge"
val isForgeLike = isNeoforge || isForge

version = "${mod.version}+mc${minecraftVersion}"
group = mod.group

val isBeta = "next" in version.toString()

val testserverDir = "../../run/testserver/${loader}"
val testClientDir = "../../run/testclient/${loader}"
val generatedResources = "src/main/generated"

base { archivesName.set("${mod.id}-${loader}") }

stonecutter {
    const("fabric", loader == "fabric")
    const("forge", loader == "forge")
    const("neoforge", loader == "neoforge")
    const("forgeLike", isForgeLike)
    val j21 = eval(minecraftVersion, ">=1.20.6")
    java {
//        withSourcesJar()
        sourceCompatibility = if (j21) JavaVersion.VERSION_21 else JavaVersion.VERSION_17
        targetCompatibility = if (j21) JavaVersion.VERSION_21 else JavaVersion.VERSION_17

        toolchain {
            if (j21) {
                languageVersion.set(JavaLanguageVersion.of(21))
            } else {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }
    }
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev")
    maven("https://maven.minecraftforge.net")
    maven("https://maven.neoforged.net/releases/")
}

if (isFabric) {
    fabricApi {
        configureDataGeneration {
            /*? if >= 1.21.4 {*/
            client = true
            /*? }*/
            outputDirectory.set(project.file(generatedResources))
        }
    }
}

if (isForgeLike) {
    sourceSets {
        main {
            resources.srcDir(project.file(generatedResources))
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
        if (isNeoforge && false) {
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
            if (isFabric) {
                vmArg("-Dfabric-api.gametest")
                vmArg("-Dfabric-api.gametest.report-file=${rootProject.file("build/junit.xml")}");
            }
            if (isForge) {
                property("forge.enabledGameTestNamespaces", mod.id)
                property("forge.enableGameTest", "true")
            }
        }
        if (!(isForge && stonecutter.eval(stonecutter.current.version, "=1.21.4"))) {
            create("gameTestServer") {
                name("Game Test Server")
                server()
                runDir = testserverDir
                if (isFabric) {
                    vmArg("-Dfabric-api.gametest")
                    vmArg("-Dfabric-api.gametest.report-file=${rootProject.file("build/junit.xml")}");
                }
                if (isForge) {
                    property("forge.enabledGameTestNamespaces", mod.id)
                    property("forge.enableGameTest", "true")
                    property("forge.gameTestServer", "true")
                }

                if (isNeoforge) {
                    property("neoforge.enabledGameTestNamespaces", mod.id)
                    property("neoforge.enableGameTest", "true")
                    property("neoforge.gameTestServer", "true")
                }
            }
        }
    }
}


if (isForge) {
    configurations.configureEach {
        resolutionStrategy.force("net.sf.jopt-simple:jopt-simple:5.0.4")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    if (!isNeoforge) {
        mappings("net.fabricmc:yarn:${mod.prop("yarn_mappings")}:v2")
    } else {
        mappings(loom.layered {
            mappings("net.fabricmc:yarn:${mod.prop("yarn_mappings")}:v2")
            mappings("dev.architectury:yarn-mappings-patch-neoforge:${mod.prop("yarn_mappings_neoforge_patch")}")
        })
    }

    if (isForge) {
        println("Adding Forge dependencies")
        "forge"("net.minecraftforge:forge:${mod.prop("forge_version")}")
    }
    if (isFabric) {
        println("Adding Fabric dependencies")
        modImplementation("net.fabricmc:fabric-loader:${mod.prop("loader_version")}")
        modApi("net.fabricmc.fabric-api:fabric-api:${mod.prop("fabric_version")}")
        modApi("net.fabricmc.fabric-api:fabric-gametest-api-v1:${mod.prop("fabric_version")}")
    }

    if (isNeoforge) {
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

    if (!(isForge && minecraftVersion.equals("1.21.4"))) {
        rootProject.tasks.register("testActiveServer") {
            group = "project"
            dependsOn(tasks.named("runGameTestServer"))
        }
    }
}


tasks.processResources {
    outputs.cacheIf { false }
    val oldResources = stonecutter.eval(stonecutter.current.version, "<1.21")

    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    println(String.format("Current version is %d", getResourceVersionFor(minecraftVersion)))

    val map = mapOf(
        "id" to mod.id,
        "name" to mod.name,
        "description" to mod.description,
        "version" to mod.version,
        "minecraftVersion" to minecraftVersion,
        "packVersion" to getResourceVersionFor(minecraftVersion),
        "fabricVersion" to mod.prop("fabric_version")
    )
    filesMatching(listOf("fabric.mod.json", "META-INF/mods.toml", "META-INF/neoforge.mods.toml")) { expand(map) }

    when {
        isFabric -> {
            exclude("META-INF/mods.toml", "META-INF/neoforge.mods.toml")
        }

        isNeoforge -> {
            exclude("fabric.mod.json", "META-INF/mods.toml")
        }

        isForge -> {
            exclude("fabric.mod.json", "META-INF/neoforge.mods.toml")
            if (oldResources) {
                filesMatching("pack.1.20.3-.mcmeta") {
                    path = path.replace("pack.1.20.3-.mcmeta", "pack.mcmeta")
                    expand(map)
                }
                exclude("pack.1.20.3+.mcmeta")
            } else {
                filesMatching("pack.1.20.3+.mcmeta") {
                    path = path.replace("pack.1.20.3+.mcmeta", "pack.mcmeta")
                    expand(map)
                }
                exclude("pack.1.20.3-.mcmeta")
            }
        }

    }


    val resourceChanges = mapOf(
        "itemIdentifier" to if (oldResources) "item" else "id"
    )

    filesMatching("data/**/*.json") {
        expand(resourceChanges)
    }

    if (oldResources) {
        println("Using old resource system")

        val renameMappings = mapOf(
            "data/minecraft/tags/block" to "data/minecraft/tags/blocks",
            "data/${mod.id}/advancement/recipe" to "data/${mod.id}/advancement/recipes",
            "data/${mod.id}/advancement" to "data/${mod.id}/advancements",
            "data/${mod.id}/loot_table" to "data/${mod.id}/loot_tables",
            "data/${mod.id}/recipe" to "data/${mod.id}/recipes",
            "data/${mod.id}/structure" to "data/${mod.id}/structures"
        )

        renameMappings.forEach { (source, destination) ->
            filesMatching("$source/**") {
                path = path.replace(source, destination)
            }
        }

        doLast {
            val buildResourcesDir = layout.buildDirectory.dir("resources/main").get().asFile

            if (buildResourcesDir.exists()) {
                println("Cleaning up empty directories in ${buildResourcesDir.path}")

                // Recursively delete empty directories
                buildResourcesDir.walkBottomUp().filter { it.isDirectory && it.listFiles().isNullOrEmpty() }
                    .forEach { dir ->
                        dir.delete()
                    }
            }
        }
    }
}



tasks.register("configureMinecraft") {
    group = "project"
    val runDir = "${rootProject.projectDir}/run"
    val optionsFile = file("$runDir/options.txt")

    doFirst {
        println("Configuring Minecraft options...")
        optionsFile.parentFile.mkdirs()
        optionsFile.writeText(
            """
            guiScale:3
            fov=90
            narrator:0
            soundCategory_music:0.0
            darkMojangStudiosBackground:true
            """.trimIndent()
        )
    }
}


tasks.named("runClient") {
    dependsOn(tasks.named("configureMinecraft"))
}

publishMods {
    changelog.set(
        rootProject.file("changelog.md").takeIf { it.exists() }?.readText() ?: "No changelog provided."
    )
    file = tasks.remapJar.get().archiveFile
    version = "${mod.version}+${loader}-${minecraftVersion}"
    type.set(if (isBeta) BETA else STABLE)
    modLoaders.add(loader)
    displayName = "${mod.version} for ${loader.upperCaseFirst()} $minecraftVersion"

    modrinth {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN").orElse("")
        projectId = providers.environmentVariable("MODRINTH_ID").orElse("0")
        minecraftVersions.add(stonecutter.current.version)
        announcementTitle = "Download ${mod.version}+${loader}-${minecraftVersion}from Modrinth"
        if (isFabric) requires("fabric-api")
    }

    curseforge {
        projectSlug = providers.environmentVariable("CURSEFORGE_SLUG").orElse("server-redstone-block")
        projectId = providers.environmentVariable("CURSEFORGE_ID").orElse("0")
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN").orElse("")
        minecraftVersions.add(stonecutter.current.version)
        clientRequired = true
        serverRequired = false
        announcementTitle = "Download ${mod.version}+${loader}-${minecraftVersion} from CurseForge"
        if (isFabric) requires("fabric-api")
    }

    dryRun = providers.environmentVariable("DO_PUBLISH").getOrElse("true").toBoolean()
}

afterEvaluate {
    // Fix for an Architectury issue with LWJGL
    tasks.runServer {
        classpath = classpath.filter { !it.toString().contains("\\org.lwjgl\\") }
    }

    if (!(isForge && minecraftVersion.equals("1.21.4"))) {
        tasks.named<JavaExec>("runGameTestServer") {
            classpath = classpath.filter { !it.toString().contains("\\org.lwjgl\\") }
        }
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:-deprecation")
}
