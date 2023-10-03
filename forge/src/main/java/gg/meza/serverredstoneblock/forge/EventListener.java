package gg.meza.serverredstoneblock.forge;

import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID)
public class EventListener {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent server) {
        ServerRedstoneBlock.onServerStarted(server.getServer());
    }
}
