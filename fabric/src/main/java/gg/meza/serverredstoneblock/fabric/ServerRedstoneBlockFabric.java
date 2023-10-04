package gg.meza.serverredstoneblock.fabric;

import gg.meza.serverredstoneblock.RedstoneBlockEntity;
import gg.meza.serverredstoneblock.RedstoneBlockItem;
import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

public class ServerRedstoneBlockFabric implements ModInitializer {
    public static final BlockEntityType<RedstoneBlockEntity> redstoneBlockEntityType = Registry.register(
            Registry.BLOCK_ENTITY_TYPE, redstoneBlockId, FabricBlockEntityTypeBuilder.create(RedstoneBlockEntity::new, block).build(null)
    );

    @Override
    public void onInitialize() {
        setEntityType(redstoneBlockEntityType);
        Registry.register(Registry.BLOCK, redstoneBlockId, block);
        RedstoneBlockItem item = Registry.register(Registry.ITEM, redstoneBlockId, new RedstoneBlockItem(block));
        ServerRedstoneBlock.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                    getWarningCommand()
            );

            dispatcher.register(
                    getOffCommand()
            );

            dispatcher.register(
                    getOnCommand()
            );
        });
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            ServerRedstoneBlock.onServerStarted(server, "fabric", FabricLoaderImpl.VERSION);
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(ServerRedstoneBlock::onServerStopping);

        ServerTickEvents.START_WORLD_TICK.register(ServerRedstoneBlock::onServerTick);
    }
}
