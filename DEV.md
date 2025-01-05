# Changing MC Versions

- Step 1: create a branch MCX.XX.X where X.XX.X matches the minecraft version
- Step 2: update files

## Update files:

- gradle.properties - update all the things
- .releaserc.json - update mc version
- .github/workflows/build.yml - update mc version
- fabric/src/main/resources/fabric.mod.json - update loader/mc version
- forge/src/main/resources/META-INF/mods.toml - update loader/mc version
- forge/src/main/resources/pack.mcmeta - update pack version


- https://maven.architectury.dev/dev/architectury/yarn-mappings-patch-neoforge/
