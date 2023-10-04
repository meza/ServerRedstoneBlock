package gg.meza.serverredstoneblock.forge;

import gg.meza.serverredstoneblock.RedstoneBlockEntity;
import gg.meza.serverredstoneblock.RedstoneBlockItem;
import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

@Mod(MOD_ID)
public class ServerRedstoneBlockForge {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    private static final RegistryObject<Block> registeredBlock = BLOCKS.register(blockName, () -> ServerRedstoneBlock.block);
    private static final RegistryObject<Item> registeredItem = ITEMS.register(blockName, () -> new RedstoneBlockItem(registeredBlock.get()));
    private static final RegistryObject<BlockEntityType<RedstoneBlockEntity>> blockEntityTypeReg = BLOCK_ENTITY_TYPE.register(blockName, () -> BlockEntityType.Builder.create(RedstoneBlockEntity::new, registeredBlock.get()).build(null));


    public ServerRedstoneBlockForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITY_TYPE.register(modEventBus);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("ServerRedstoneBlockForge onCommonSetup");
        setEntityType(blockEntityTypeReg.get());
        ServerRedstoneBlock.init();
    }
}
