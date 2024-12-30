package gg.meza.serverredstoneblock;

import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import java.util.Optional;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

/*? if forge {*/
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.GameTestPrefix;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

@GameTestHolder(MOD_ID)
/*?}*/
public class E2ETests {
    public static final BlockPos TEST_REDSTONE_BLOCK = new BlockPos(1, 2, 2);
    public static final BlockPos MAIN_REDSTONE = new BlockPos(2, 2, 2);
    public static final BlockPos SECONDARY_REDSTONE = new BlockPos(3, 2, 2);

    public E2ETests() {
        LOGGER.info("E2E Tests Initialized");
    }

    @GameTest(templateName = MOD_ID+":state-test")
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

    @GameTest(templateName =  MOD_ID+":multi-block")
    public static void multiBlockStateTest(TestContext ctx) {
        final BlockPos DUST1 = new BlockPos(1, 2, 0);
        final BlockPos DUST2 = new BlockPos(1, 2, 2);
        final BlockPos DUST3 = new BlockPos(1, 2, 4);

        assertPower(ctx, DUST1, 15);
        assertPower(ctx, DUST2, 15);
        assertPower(ctx, DUST3, 15);

        ServerRedstoneBlock.currentState.warning();

        ctx.runAtTick(24, () -> {
            assertPower(ctx, DUST1, 1);
            assertPower(ctx, DUST2, 1);
            assertPower(ctx, DUST3, 1);

            ServerRedstoneBlock.currentState.off();
        });

        ctx.runAtTick(48, () -> {
            assertPower(ctx, DUST1, 0);
            assertPower(ctx, DUST2, 0);
            assertPower(ctx, DUST3, 0);

            ServerRedstoneBlock.currentState.on();
        });

        ctx.runAtTick(72, () -> {
            assertPower(ctx, DUST1, 15);
            assertPower(ctx, DUST2, 15);
            assertPower(ctx, DUST3, 15);
            ctx.complete();
        });

    }

    @GameTest(templateName =  MOD_ID+":directions")
    public static void directionality(TestContext ctx) {
        final BlockPos MAIN_BLOCK = new BlockPos(2, 2, 2);
        final BlockPos DUST1 = MAIN_BLOCK.east();
        final BlockPos DUST2 = MAIN_BLOCK.west();
        final BlockPos DUST3 = MAIN_BLOCK.north();
        final BlockPos DUST4 = MAIN_BLOCK.south();

        assertPower(ctx, DUST1, 15);
        assertPower(ctx, DUST2, 15);
        assertPower(ctx, DUST3, 15);
        assertPower(ctx, DUST4, 15);

        ServerRedstoneBlock.currentState.warning();

        ctx.runAtTick(24, () -> {
            assertPower(ctx, DUST1, 1);
            assertPower(ctx, DUST2, 1);
            assertPower(ctx, DUST3, 1);
            assertPower(ctx, DUST4, 1);

            ServerRedstoneBlock.currentState.off();
        });

        ctx.runAtTick(48, () -> {
            assertPower(ctx, DUST1, 0);
            assertPower(ctx, DUST2, 0);
            assertPower(ctx, DUST3, 0);
            assertPower(ctx, DUST4, 0);

            ServerRedstoneBlock.currentState.on();
        });

        ctx.runAtTick(72, () -> {
            assertPower(ctx, DUST1, 15);
            assertPower(ctx, DUST2, 15);
            assertPower(ctx, DUST3, 15);
            assertPower(ctx, DUST4, 15);
            ctx.complete();
        });

    }

    @GameTest(templateName =  MOD_ID+":empty")
    public static void isRecipePresent(TestContext ctx) {
        Optional<RecipeEntry<?>> recipe = ctx.getWorld().getServer().getRecipeManager().get(new Identifier(MOD_ID,"server_redstone_block"));
        ctx.assertTrue(recipe.isPresent(), String.format("Recipe is not present. Got %s", recipe));
        ctx.complete();
    }

    private static void assertPower(TestContext ctx, BlockPos pos, int expected) {
        Integer actual = ctx.getBlockState(pos).get(RedstoneWireBlock.POWER);
        ctx.assertTrue(actual == expected, String.format("Block at %s should have power %d but has %d", pos, expected, actual));
    }
}
