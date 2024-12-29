package gg.meza.serverredstoneblock;

import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.LOGGER;
/*? if forge {*/
/*import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.GameTestPrefix;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

@GameTestHolder(MOD_ID)
*//*?}*/
public class E2ETests {
    public static final BlockPos TEST_REDSTONE_BLOCK = new BlockPos(1, 2, 2);
    public static final BlockPos MAIN_REDSTONE = new BlockPos(2, 2, 2);
    public static final BlockPos SECONDARY_REDSTONE = new BlockPos(3, 2, 2);

    public E2ETests() {
        LOGGER.info("E2E Tests Initialized");
    }

    @GameTest(templateName = /*? if forge {*//*MOD_ID + ":"+*//*?}*/"state-test")
    public static void stateTest(TestContext ctx) {
        Integer power = ctx.getBlockState(MAIN_REDSTONE).get(RedstoneWireBlock.POWER);
        ctx.assertTrue(power == 15, "Main redstone wire should be powered but is " + power);

        ServerRedstoneBlock.currentState.warning();

        ctx.runAtTick(24, () -> {
            Integer actual = ctx.getBlockState(MAIN_REDSTONE).get(RedstoneWireBlock.POWER);
            ctx.assertTrue(actual == 1, "Main redstone wire should be in a warning state but is " + actual);

            ServerRedstoneBlock.currentState.off();
        });

        ctx.runAtTick(48, () -> {
            Integer actual = ctx.getBlockState(MAIN_REDSTONE).get(RedstoneWireBlock.POWER);
            ctx.assertTrue(actual == 0, "Main redstone wire should not be powered but is " + actual);

            ServerRedstoneBlock.currentState.on();
        });

        ctx.runAtTick(72, () -> {
            Integer actual = ctx.getBlockState(MAIN_REDSTONE).get(RedstoneWireBlock.POWER);
            ctx.assertTrue(actual == 15, "Main redstone wire should be fully powered but is " + actual);
            ctx.complete();
        });
    }
}
