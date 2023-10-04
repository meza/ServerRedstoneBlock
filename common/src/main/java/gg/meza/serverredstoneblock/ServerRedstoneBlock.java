package gg.meza.serverredstoneblock;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.literal;

public class ServerRedstoneBlock {
    public static final String VERSION = "VERSION_REPL";
    public static final String blockName = "server_redstone_block";
    public static final String MOD_ID = "serverredstoneblock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier redstoneBlockId = new Identifier(MOD_ID, blockName);

    public static final RedstoneBlock block = new RedstoneBlock();
    public static BlockEntityType<RedstoneBlockEntity> redstoneBlockEntityType;

    public static final String COMMAND_BASE = "srs";

    public static Analytics analytics = new Analytics();

    public static void setEntityType(BlockEntityType<RedstoneBlockEntity> entityType) {
        redstoneBlockEntityType = entityType;
    }

    public static void init() {
        LOGGER.info("ServerRedstoneBlock init");
    }

    public static LiteralArgumentBuilder<ServerCommandSource> getOnCommand() {
        return literal(COMMAND_BASE).then(literal("on").executes(context -> {
            block.on();
            return 1;
        })).requires(source -> source.hasPermissionLevel(4));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> getOffCommand() {
        return literal(COMMAND_BASE).then(literal("off").executes(context -> {
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
        String worldId = WorldInfoSaveData.getWorldId(server);
        analytics.setLoader(loader, loaderVersion);
        analytics.setMinecraftVersion(server.getVersion());
        analytics.setWorldId(worldId);
        analytics.serverStartedEvent();
    }

    public static void onServerStopping(MinecraftServer server) {
        block.off();
        analytics.flush();
    }
}
