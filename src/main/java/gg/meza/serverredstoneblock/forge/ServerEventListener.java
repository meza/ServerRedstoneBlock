/*? if forge {*/
/*package gg.meza.serverredstoneblock.forge;

import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;


@Mod.EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID)
public class ServerEventListener {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent server) {
        ServerRedstoneBlock.onServerStarted(server.getServer(), "forge", FMLLoader.versionInfo().forgeVersion());
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
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        ServerRedstoneBlock.onServerTick(event.getServer().getOverworld());
    }
}

*//*?}*/
