/*? if forge {*/
/*package gg.meza.serverredstoneblock.forge;

import gg.meza.serverredstoneblock.E2ETests;
import gg.meza.serverredstoneblock.RedstoneBlockEntity;
import gg.meza.serverredstoneblock.RedstoneBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

public class RegistryHelper {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<Block> REDSTONE_BLOCK = registerBlock(blockName, blockSupplier, RedstoneBlockItem.class);

    public static final RegistryObject<BlockEntityType<RedstoneBlockEntity>> REDSTONE_BLOCK_ENTITY = BLOCK_ENTITY_TYPE.register(blockName, () -> BlockEntityType.Builder.create(RedstoneBlockEntity::new, REDSTONE_BLOCK.get()).build(null));

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
        BLOCK_ENTITY_TYPE.register(modEventBus);
    }
}
*//*?}*/
