/*? if neoforge {*/
/*package gg.meza.serverredstoneblock.neoforge;

import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;


@EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID)
public class NeoForgeEventHandlers {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent server) {
        LOGGER.info("Server started from the event bus");
        ServerRedstoneBlock.onServerStarted(server.getServer(), "forge", FMLLoader.versionInfo().neoForgeVersion());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent server) {
        ServerRedstoneBlock.onServerStopping(server.getServer());
    }

    @SubscribeEvent
    public static void onCommands(RegisterCommandsEvent event) {
        LOGGER.info("Commands are being registered");
        event.getDispatcher().register(getWarningCommand());
        event.getDispatcher().register(getOnCommand());
        event.getDispatcher().register(getOffCommand());
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        ServerRedstoneBlock.onServerTick(event.getServer().getOverworld());
    }
}
*//*?}*/
