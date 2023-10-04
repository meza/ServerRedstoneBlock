#!/bin/sh

VERSION=$1

echo "Replacing version with ${VERSION}"
sed -e "s/VERSION/${VERSION}/" -i gradle.properties
sed -e "s/VERSION_REPL/${VERSION}/" -i common/src/main/java/gg/meza/serverredstoneblock/ServerRedstoneBlock.java
sed -e "s/POSTHOG_API_KEY_REPL/${POSTHOG_API_KEY}/" -i common/src/main/java/gg/meza/serverredstoneblock/Analytics.java
./gradlew build -x test # we've ran the tests earlier in the build
