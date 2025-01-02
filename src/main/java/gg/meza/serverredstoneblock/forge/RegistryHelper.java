/*? if forge {*/
/*package gg.meza.serverredstoneblock.forge;

import gg.meza.serverredstoneblock.RedstoneBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

public class RegistryHelper {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Block> REDSTONE_BLOCK = registerBlock(blockName, blockSupplier, RedstoneBlockItem.class);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Class<? extends BlockItem> blockItemClass) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, blockItemClass);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, Class<? extends BlockItem> blockItemClass) {
        return ITEMS.register(name, () -> {
            try {
                return blockItemClass.getConstructor(Block.class, Item.Settings.class).newInstance(block.get(), new Item.Settings());
            } catch (Exception e) {
                throw new RuntimeException("Failed to create block item", e);
            }
        });
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
*//*?}*/
