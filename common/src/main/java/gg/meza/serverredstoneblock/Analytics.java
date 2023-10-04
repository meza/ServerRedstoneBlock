package gg.meza.serverredstoneblock;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Analytics {
    //    private static final String POSTHOG_API_KEY = "POSTHOG_API_KEY_REPL";
    private static final String POSTHOG_API_KEY = "phc_gydkp9wcXJnWaxxGx1W30VP0f9KYAXQS8YqEOvjrTKj";
    private static final String POSTHOG_HOST = "https://eu.posthog.com";
    private final String OS_NAME = System.getProperty("os.name");
    private String MC_VERSION;
    private final String JAVA_VERSION = System.getProperty("java.version");
    private String loaderVersion;
    private String worldId;
    private String loader;
    private int craftedCount = 0;

    public Analytics() {
    }

    private void sendEvent(String event) {
        sendEvent(event, new NameValuePair[]{});
    }

    private void sendEvent(String event, NameValuePair[] props) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(POSTHOG_HOST + "/capture");
            post.addHeader("Content-Type", "application/json");

            String data = "{\"api_key\": \"" + POSTHOG_API_KEY + "\", \"distinct_id\": \"" + this.worldId + "\", \"event\": \"" + event + "\", \"properties\": {";
            for (NameValuePair prop : props) {
                data += "\"" + prop.getName() + "\": \"" + prop.getValue() + "\",";
            }

            data += "\"Minecraft Version\": \"" + MC_VERSION + "\",";
            data += "\"Minecraft Loader\": \"" + loader + "\",";
            data += "\"Minecraft Loader Version\": \"" + loaderVersion + "\",";
            data += "\"OS\": \"" + OS_NAME + "\",";
            data += "\"Local Time\": \"" + new java.util.Date().toString() + "\",";
            data += "\"Java Version\": \"" + JAVA_VERSION + "\"";

            data += "}}";

            post.setEntity(new StringEntity(data));

            client.execute(post);

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
        sendEvent("Server Redstone Block Toggled", new NameValuePair[]{new BasicNameValuePair("state", state.toString())});
    }

    public void flush() {
        if (craftedCount == 0) return;
        sendEvent("Server Redstone Block Crafted", new NameValuePair[]{new BasicNameValuePair("count", String.valueOf(craftedCount))});
    }
}
