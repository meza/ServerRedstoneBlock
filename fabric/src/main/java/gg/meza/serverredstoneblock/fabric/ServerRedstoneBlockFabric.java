package gg.meza.serverredstoneblock.fabric;

import gg.meza.serverredstoneblock.RedstoneBlockEntity;
import gg.meza.serverredstoneblock.RedstoneBlockItem;
import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

public class ServerRedstoneBlockFabric implements ModInitializer {
    public static final BlockEntityType<RedstoneBlockEntity> redstoneBlockEntityType = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, redstoneBlockId, BlockEntityType.Builder.create(RedstoneBlockEntity::new, block).build(null)
    );

    @Override
    public void onInitialize() {
        setEntityType(redstoneBlockEntityType);
        Registry.register(Registries.BLOCK, redstoneBlockId, block);
        Registry.register(Registries.ITEM, redstoneBlockId, new RedstoneBlockItem(block));
        ServerRedstoneBlock.init();
        ServerLifecycleEvents.SERVER_STARTED.register(ServerRedstoneBlock::onServerStarted);
    }
}
