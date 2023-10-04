package gg.meza.serverredstoneblock.forge;

import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

@Mod.EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID)
public class EventListener {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent server) {
        ServerRedstoneBlock.onServerStarted(server.getServer());
    }

    @SubscribeEvent
    public static void onCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(getWarningCommand());
        event.getDispatcher().register(getOnCommand());
        event.getDispatcher().register(getOffCommand());
    }
}
