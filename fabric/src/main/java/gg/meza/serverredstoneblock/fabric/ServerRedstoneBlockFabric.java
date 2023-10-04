package gg.meza.serverredstoneblock.fabric;

import gg.meza.serverredstoneblock.RedstoneBlockEntity;
import gg.meza.serverredstoneblock.RedstoneBlockItem;
import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
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
        RedstoneBlockItem item = Registry.register(Registries.ITEM, redstoneBlockId, new RedstoneBlockItem(block));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(item));
        ServerRedstoneBlock.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> {
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
        ServerLifecycleEvents.SERVER_STARTED.register(ServerRedstoneBlock::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register(ServerRedstoneBlock::onServerStopping);
    }
}
