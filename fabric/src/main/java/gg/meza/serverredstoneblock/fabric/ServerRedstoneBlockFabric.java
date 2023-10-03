package gg.meza.serverredstoneblock.fabric;

import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ServerRedstoneBlockFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerRedstoneBlock.init();
        ServerLifecycleEvents.SERVER_STARTED.register(ServerRedstoneBlock::onServerStarted);
    }
}
