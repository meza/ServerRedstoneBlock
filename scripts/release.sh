#!/bin/sh

VERSION=$1

echo "Replacing version with ${VERSION}"
sed -e "s/0.0-SNAPSHOT/${VERSION}/" -i gradle.properties
sed -e "s/VERSION_REPL/${VERSION}/" -i src/main/java/gg/meza/serverredstoneblock/ServerRedstoneBlock.java
sed -e "s/POSTHOG_API_KEY_REPL/${POSTHOG_API_KEY}/" -i src/main/java/gg/meza/serverredstoneblock/Telemetry.java

./gradlew chiseledGameTest chiseledBuildAndCollect chiseledPublishMods --stacktrace
