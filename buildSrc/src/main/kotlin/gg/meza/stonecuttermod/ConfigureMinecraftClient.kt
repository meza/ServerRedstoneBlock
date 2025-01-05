package gg.meza.stonecuttermod

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.createFile

/** Configures the Minecraft client
 *
 * Set some basic options on the minecraft client
 */
abstract class ConfigureMinecraftClient : DefaultTask() {
    @Input
    val runDirectory = project.objects.property(String::class.java)

    @Input
    val guiScale = project.objects.property(Int::class.java)

    @Input
    val fov = project.objects.property(Int::class.java)

    @Input
    val narrator = project.objects.property(Boolean::class.java)

    @Input
    val musicVolume = project.objects.property(Double::class.java)

    @Input
    val darkBackground = project.objects.property(Boolean::class.java)

    @Input
    val additionalLines = project.objects.mapProperty(String::class.java, String::class.java)

    init {
        group = "minecraft"
        description = "Configures the Minecraft client"

        guiScale.convention(3)
        fov.convention(90)
        narrator.convention(false)
        musicVolume.convention(0.0)
        darkBackground.convention(true)
        runDirectory.convention(project.rootProject.layout.projectDirectory.file("run").toString())
    }

    @TaskAction
    fun run() {
        val directory = Files.createDirectories(Path(runDirectory.get()))

        println("Configuring Minecraft options in ${directory}...")
        val optionsFile = directory.resolve("options.txt")

        if (!Files.exists(optionsFile)) {
            optionsFile.createFile()
        }
        val optionsFileActual = optionsFile.toFile()

        optionsFileActual.writeText(
            """
            guiScale:${guiScale.get()}
            fov:${fov.get()}
            narrator:${if (narrator.get()) "1" else "0"}
            soundCategory_music:${musicVolume.get()}
            darkMojangStudiosBackground:${if (darkBackground.get()) "1" else "0"}
            """.trimIndent()
        )

        additionalLines.get().forEach { (key, value) ->
            run {
                var actualValue = value
                if (value.contains(" ")) actualValue = "\"$value\""
                optionsFileActual.appendText("\n$key:$actualValue")

            }
        }
    }
}
