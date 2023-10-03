package gg.meza.serverredstoneblock.forge;

import dev.architectury.platform.forge.EventBuses;
import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ServerRedstoneBlock.MOD_ID)
public class ServerRedstoneBlockForge {
    public ServerRedstoneBlockForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ServerRedstoneBlock.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ServerRedstoneBlock.init();
    }
}
