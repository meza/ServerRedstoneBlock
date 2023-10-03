package gg.meza.serverredstoneblock;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static net.minecraft.commands.Commands.literal;

public class ServerRedstoneBlock {
    public static final String blockName = "server_redstone_block";
    public static final String MOD_ID = "serverredstoneblock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final BlockBehaviour.Properties blockProps = BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL);
    public static ResourceLocation redstoneBlockId = new ResourceLocation(MOD_ID, blockName);

    public static final DeferredRegister<Block> blocks = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<Item> items = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final DeferredRegister<BlockEntityType<?>> blockEntities = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<Block> block = blocks.register(blockName, () -> new RedstoneBlock(blockProps));
    public static final RegistrySupplier<Item> item = items.register(blockName, () -> new RedstoneBlockItem(block.get()));
    public static final RegistrySupplier<BlockEntityType<?>> redstoneBlockEntityType = blockEntities.register(blockName, () -> BlockEntityType.Builder.of(RedstoneBlockEntity::new, block.get()).build(null));

    public static final String COMMAND_BASE = "srs";

    public static Analytics analytics = new Analytics();

    public static void init() {
        LOGGER.info("ServerRedstoneBlock init");

        blocks.register();
        blockEntities.register();
        items.register();

        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> {
            dispatcher.register(
                    literal(COMMAND_BASE).then(literal("warning").executes(context -> {
                        ((RedstoneBlock) Objects.requireNonNull(blocks.getRegistrar().get(redstoneBlockId))).warning();
                        return 1;
                    })).requires(source -> source.hasPermission(4))
            );

            dispatcher.register(
                    literal(COMMAND_BASE).then(literal("off").executes(context -> {
                        ((RedstoneBlock) Objects.requireNonNull(blocks.getRegistrar().get(redstoneBlockId))).off();
                        return 1;
                    })).requires(source -> source.hasPermission(4))
            );

            dispatcher.register(
                    literal(COMMAND_BASE).then(literal("on").executes(context -> {
                        ((RedstoneBlock) Objects.requireNonNull(blocks.getRegistrar().get(redstoneBlockId))).on();
                        return 1;
                    })).requires(source -> source.hasPermission(4))
            );
        });

    }

    public static void onServerStarted(MinecraftServer server) {
        WorldInfoSaveData data = server.overworld().getDataStorage().computeIfAbsent(WorldInfoSaveData::load, WorldInfoSaveData::create, "serverredstoneblock");
        analytics.setMinecraftVersion(server.getServerVersion());
        analytics.setWorldId(data.worldId);
        analytics.serverStartedEvent();
    }
}
