package gg.meza.serverredstoneblock;

/*? if fabric {*/
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
/*?}*/


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.literal;

/*? if fabric {*/
public class ServerRedstoneBlock implements ModInitializer {
/*?}*/

/*? if forge {*/
/*public class ServerRedstoneBlock {
*//*?}*/

    public static final String VERSION = "VERSION_REPL";
    public static final String blockName = "server_redstone_block";
    public static final String MOD_ID = "serverredstoneblock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final RedstoneBlock block = new RedstoneBlock();

    public static final String COMMAND_BASE = "srs";
    public static Telemetry telemetry;
    private static int tick = 0;
    private static int MINUTE_IN_TICKS = 1200;

    /*? if fabric {*/
    public static Identifier redstoneBlockId = new Identifier(MOD_ID, blockName);
    public static final BlockEntityType<RedstoneBlockEntity> redstoneBlockEntityType = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, redstoneBlockId, BlockEntityType.Builder.create(RedstoneBlockEntity::new, block).build(null)
    );
    /*?}*/

    /*? if forge {*/
    /*public static BlockEntityType<RedstoneBlockEntity> redstoneBlockEntityType;
    public static void setEntityType(BlockEntityType<RedstoneBlockEntity> entityType) {
        redstoneBlockEntityType = entityType;
    }
    *//*?}*/

    //COMMON

    public static void init() {
        LOGGER.info("ServerRedstoneBlock init");
        telemetry = new Telemetry();
    }

    public static LiteralArgumentBuilder<ServerCommandSource> getOnCommand() {
        return literal(COMMAND_BASE).then(literal("on").executes(context -> {
            block.on();
            return 1;
        })).requires(source -> source.hasPermissionLevel(4));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> getOffCommand() {
        return literal(COMMAND_BASE).then(literal("off").executes(context -> {
            LOGGER.info("ServerRedstoneBlock off");
            block.off();
            return 1;
        })).requires(source -> source.hasPermissionLevel(4));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> getWarningCommand() {
        return literal(COMMAND_BASE).then(literal("warning").executes(context -> {
            block.warning();
            return 1;
        })).requires(source -> source.hasPermissionLevel(4));
    }

    public static void onServerStarted(MinecraftServer server, String loader, String loaderVersion) {
        telemetry.enable();
        String worldId = WorldInfoSaveData.getWorldId(server);
        telemetry.setLoader(loader, loaderVersion);
        telemetry.setMinecraftVersion(server.getVersion());
        telemetry.setWorldId(worldId);
        telemetry.serverStartedEvent();
    }

    public static void onServerStopping(MinecraftServer server) {
        block.off();
        telemetry.flush();
    }

    public static void onServerTick(World world) {
        if (tick++ > (MINUTE_IN_TICKS * 10)) {
            telemetry.flush();
            tick = 0;
        }
    }

    /*? if fabric {*/
    @Override
    public void onInitialize() {
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
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            ServerRedstoneBlock.onServerStarted(server, "fabric", FabricLoaderImpl.VERSION);
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(ServerRedstoneBlock::onServerStopping);

        ServerTickEvents.START_WORLD_TICK.register(ServerRedstoneBlock::onServerTick);
    }
    /*?}*/




}
