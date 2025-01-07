/*? if neoforge {*/
package gg.meza.serverredstoneblock.neoforge;

import gg.meza.serverredstoneblock.RedstoneBlockItem;
import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

public class RegistryHelper {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredBlock<Block> REDSTONE_BLOCK = registerBlock(blockName, blockSupplier, RedstoneBlockItem.class);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, Class<? extends BlockItem> blockItemClass) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, blockItemClass);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, Class<? extends BlockItem> blockItemClass) {
        ITEMS.register(name, () -> {
            try {

                /*? if >=1.21.4 {*/
                Identifier id = Identifier.of(ServerRedstoneBlock.MOD_ID, name);
                RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
                /*?}*/
                return blockItemClass.getConstructor(Block.class, Item.Settings.class).newInstance(block.get(), new Item.Settings()/*? if >=1.21.4 {*/.registryKey(key).useBlockPrefixedTranslationKey()/*?}*/);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create block item", e);
            }
        });
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
    }
}
/*?}*/
