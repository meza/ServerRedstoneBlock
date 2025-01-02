/*? if fabric {*/
/*package gg.meza.serverredstoneblock.fabric;

import gg.meza.serverredstoneblock.RedstoneBlockItem;
import gg.meza.serverredstoneblock.ServerRedstoneBlock;
/^? if 1.20.2 {^/
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
/^?}^/
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
/^? if >=1.21.2 {^/
/^import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
^//^?}^/
import net.minecraft.util.Identifier;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

public class RegistryHelper {

    public static final Block REDSTONE_BLOCK = registerBlock(blockName, blockSupplier.get(), RedstoneBlockItem.class);

    public static Block registerBlock(String name, Block block, Class<? extends BlockItem> blockItemClass) {
        registerBlockItem(name, block, blockItemClass);
        /^? if <1.21 {^/
        Identifier id = new Identifier(ServerRedstoneBlock.MOD_ID, name);
        /^?} else {^/
        /^Identifier id = Identifier.of(ServerRedstoneBlock.MOD_ID, name);
        ^//^?}^/
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static Item registerBlockItem(String name, Block block, Class<? extends BlockItem> blockItemClass) {
        /^? if <1.21 {^/
        Identifier id = new Identifier(ServerRedstoneBlock.MOD_ID, name);
        FabricItemSettings settings = new FabricItemSettings();

        /^?} else {^/
        /^Identifier id = Identifier.of(ServerRedstoneBlock.MOD_ID, name);
        /^¹? if >=1.21.2 {¹^/
        /^¹RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        ¹^//^¹?}¹^/
        Item.Settings settings = new Item.Settings()/^¹? if >=1.21.2 {¹^//^¹.registryKey(key).useBlockPrefixedTranslationKey()¹^//^¹?}¹^/;
        ^//^?}^/
        try {
            BlockItem blockItem = blockItemClass.getConstructor(Block.class, Item.Settings.class).newInstance(block, settings);
            return Registry.register(Registries.ITEM, id, blockItem);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create block item", e);
        }    }

    public static void registerBlockAndItems() {
        ServerRedstoneBlock.LOGGER.info("Registering blocks and items for " + ServerRedstoneBlock.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(REDSTONE_BLOCK));
    }
}

*//*?}*/
