package gg.meza.serverredstoneblock;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.VERSION;

public class Analytics {
    private static final String POSTHOG_API_KEY = "POSTHOG_API_KEY_REPL";
    private static final String POSTHOG_HOST = "https://eu.posthog.com";
    private final String OS_NAME = System.getProperty("os.name");
    private String MC_VERSION;
    private final String JAVA_VERSION = System.getProperty("java.version");
    private String loaderVersion;
    private String worldId;
    private String loader;
    private int craftedCount = 0;
    private boolean enabled = false;

    public Analytics() {
    }

    public void enable() {
        this.enabled = true;
    }

    private void sendEvent(String event) {
        sendEvent(event, Map.of());
    }

    private void sendEvent(String event, Map<String, String> props) {
        if (!enabled) return;

        try {
            String data = "{\"api_key\": \"" + POSTHOG_API_KEY + "\", \"distinct_id\": \"" + this.worldId + "\", \"event\": \"" + event + "\", \"properties\": {";
            for (Map.Entry<String, String> prop : props.entrySet()) {
                data += "\"" + prop.getKey() + "\": \"" + prop.getValue() + "\",";
            }

            data += "\"Minecraft Version\": \"" + MC_VERSION + "\",";
            data += "\"Minecraft Loader\": \"" + loader + "\",";
            data += "\"Minecraft Loader Version\": \"" + loaderVersion + "\",";
            data += "\"Mod Version\": \"" + VERSION + "\",";
            data += "\"OS\": \"" + OS_NAME + "\",";
            data += "\"Local Time\": \"" + new java.util.Date().toString() + "\",";
            data += "\"Java Version\": \"" + JAVA_VERSION + "\"";

            data += "}}";

            HttpRequest post = HttpRequest.newBuilder()
                    .uri(new URI(POSTHOG_HOST + "/capture"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpClient.newBuilder().build().send(post, HttpResponse.BodyHandlers.discarding());

        } catch (Exception e) {
            //noop
        }
    }

    public void setWorldId(String worldId) {
        this.worldId = worldId;
    }

    public void setLoader(String loader, String loaderVersion) {
        this.loader = loader;
        this.loaderVersion = loaderVersion;
    }

    public void setMinecraftVersion(String minecraftVersion) {
        this.MC_VERSION = minecraftVersion;
    }

    public void serverStartedEvent() {
        sendEvent("Server Started");
    }

    public void redstoneBlockCrafted() {
        craftedCount++;
    }

    public void redstoneBlockPlaced() {
        sendEvent("Server Redstone Block Placed");
    }

    public void redstoneBlockBroken() {
        sendEvent("Server Redstone Block Broken");
    }

    public void redstoneToggled(ServerPowerState state) {
        sendEvent("Server Redstone Block Toggled", Map.of("state", state.toString()));
    }

    public void flush() {
        if (craftedCount == 0) return;
        sendEvent("Server Redstone Block Crafted", Map.of("count", String.valueOf(craftedCount)));
        craftedCount = 0;
    }
}
