package gg.meza.serverredstoneblock;

/*? if fabric {*/
/*import gg.meza.serverredstoneblock.fabric.RegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;
*//*?}*/

/*? if forge {*/
import gg.meza.serverredstoneblock.forge.RegistryHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.item.ItemGroups;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
/*?}*/

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;
import static net.minecraft.server.command.CommandManager.literal;

/*? if fabric {*/
/*public class ServerRedstoneBlock implements ModInitializer {
*//*?}*/

/*? if forge {*/
@Mod(MOD_ID)
public class ServerRedstoneBlock {
/*?}*/

    public static final String VERSION = "VERSION_REPL";
    public static final String blockName = "server_redstone_block";
    public static final String MOD_ID = "serverredstoneblock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final CurrentState currentState = new CurrentState();
    public static final String COMMAND_BASE = "srs";
    public static Telemetry telemetry;
    private static int tick = 0;
    private static int MINUTE_IN_TICKS = 1200;
    public static final Supplier<Block> blockSupplier = () -> new RedstoneBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED)
            .requiresTool()
            .strength(5.0F, 6.0F)
            .sounds(BlockSoundGroup.METAL));

    /*? if forge {*/
    public ServerRedstoneBlock() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::addItemToCreativeTab);

        RegistryHelper.register(modEventBus);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        ServerRedstoneBlock.init();
    }

    private void addItemToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == ItemGroups.REDSTONE) {
            event.accept(RegistryHelper.REDSTONE_BLOCK);
        }
    }

    /*?}*/


    //COMMON

    public static void init() {
        LOGGER.info("ServerRedstoneBlock init");
        telemetry = new Telemetry();
    }

    public static LiteralArgumentBuilder<ServerCommandSource> getOnCommand() {
        return literal(COMMAND_BASE).then(literal("on").executes(context -> {
            currentState.on();
            return 1;
        })).requires(source -> source.hasPermissionLevel(4));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> getOffCommand() {
        return literal(COMMAND_BASE).then(literal("off").executes(context -> {
            LOGGER.info("ServerRedstoneBlock off");
            currentState.off();
            return 1;
        })).requires(source -> source.hasPermissionLevel(4));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> getWarningCommand() {
        return literal(COMMAND_BASE).then(literal("warning").executes(context -> {
            currentState.warning();
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
        currentState.off();
        telemetry.flush();
    }

    public static void onServerTick(World world) {
        if (tick++ > (MINUTE_IN_TICKS * 10)) {
            telemetry.flush();
            tick = 0;
        }
    }

    /*? if fabric {*/
    /*@Override
    public void onInitialize() {
        RegistryHelper.registerBlockAndItems();
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
    *//*?}*/




}
