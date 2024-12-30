/*? if fabric {*/
/*package gg.meza.serverredstoneblock.fabric;

import gg.meza.serverredstoneblock.RedstoneBlockEntity;
import gg.meza.serverredstoneblock.RedstoneBlockItem;
import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.blockName;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.blockSupplier;

public class RegistryHelper {

    public static final Block REDSTONE_BLOCK = registerBlock(blockName, blockSupplier.get(), RedstoneBlockItem.class);


    public static final BlockEntityType<RedstoneBlockEntity> REDSTONE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ServerRedstoneBlock.MOD_ID, blockName), BlockEntityType.Builder.create(RedstoneBlockEntity::new, REDSTONE_BLOCK).build(null));

    public static Block registerBlock(String name, Block block, Class<? extends BlockItem> blockItemClass) {
        registerBlockItem(name, block, blockItemClass);
        return Registry.register(Registries.BLOCK, new Identifier(ServerRedstoneBlock.MOD_ID, name), block);
    }

    public static Item registerBlockItem(String name, Block block, Class<? extends BlockItem> blockItemClass) {
        try {
            BlockItem blockItem = blockItemClass.getConstructor(Block.class, Item.Settings.class).newInstance(block, new FabricItemSettings());
            return Registry.register(Registries.ITEM, new Identifier(ServerRedstoneBlock.MOD_ID, name), blockItem);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create block item", e);
        }    }

    public static void registerBlockAndItems() {
        ServerRedstoneBlock.LOGGER.info("Registering blocks and items for " + ServerRedstoneBlock.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(REDSTONE_BLOCK));
    }
}

*//*?}*/
