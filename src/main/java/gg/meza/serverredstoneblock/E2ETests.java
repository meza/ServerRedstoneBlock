package gg.meza.serverredstoneblock;

import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.test.GameTest;
import net.minecraft.test.StructureTestUtil;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

/*? if forge {*/
import net.minecraftforge.gametest.GameTestHolder;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

@GameTestHolder(MOD_ID)
/*?}*/
public class E2ETests {
    public static final BlockPos TEST_REDSTONE_BLOCK = new BlockPos(1, 2, 2);
    public static final BlockPos MAIN_REDSTONE = new BlockPos(2, 2, 2);
    public static final BlockPos SECONDARY_REDSTONE = new BlockPos(3, 2, 2);
    public static final int FIRST_TICK = 22;
    public static final int SECOND_TICK = 44;
    public static final int THIRD_TICK = 66;
    public static final int FOURTH_TICK = 88;

    @GameTest(templateName = MOD_ID + ":state-test", batchId = "state-test")
    public static void stateTest(TestContext ctx) {
        ServerRedstoneBlock.currentState.on();

        ctx.runAtTick(FIRST_TICK, () -> {
            assertPower(ctx, MAIN_REDSTONE, 15);
            ServerRedstoneBlock.currentState.warning();
        });

        ctx.runAtTick(SECOND_TICK, () -> {
            assertPower(ctx, MAIN_REDSTONE, 1);
            ServerRedstoneBlock.currentState.off();
        });

        ctx.runAtTick(THIRD_TICK, () -> {
            assertPower(ctx, MAIN_REDSTONE, 0);

            ServerRedstoneBlock.currentState.on();
        });

        ctx.runAtTick(FOURTH_TICK, () -> {
            assertPower(ctx, MAIN_REDSTONE, 15);
            ServerRedstoneBlock.currentState.on();
            ctx.complete();
        });
    }

    @GameTest(templateName = MOD_ID + ":multi-block", batchId = "multi-block")
    public static void multiBlockStateTest(TestContext ctx) {
        ServerRedstoneBlock.currentState.on();

        final BlockPos DUST1 = new BlockPos(1, 2, 0);
        final BlockPos DUST2 = new BlockPos(1, 2, 2);
        final BlockPos DUST3 = new BlockPos(1, 2, 4);

        ctx.runAtTick(FIRST_TICK, () -> {
            assertPower(ctx, DUST1, 15);
            assertPower(ctx, DUST2, 15);
            assertPower(ctx, DUST3, 15);

            ServerRedstoneBlock.currentState.warning();
        });

        ctx.runAtTick(SECOND_TICK, () -> {
            assertPower(ctx, DUST1, 1);
            assertPower(ctx, DUST2, 1);
            assertPower(ctx, DUST3, 1);

            ServerRedstoneBlock.currentState.off();
        });

        ctx.runAtTick(THIRD_TICK, () -> {
            assertPower(ctx, DUST1, 0);
            assertPower(ctx, DUST2, 0);
            assertPower(ctx, DUST3, 0);

            ServerRedstoneBlock.currentState.on();
        });

        ctx.runAtTick(FOURTH_TICK, () -> {
            assertPower(ctx, DUST1, 15);
            assertPower(ctx, DUST2, 15);
            assertPower(ctx, DUST3, 15);
            ServerRedstoneBlock.currentState.on();
            ctx.complete();
        });

    }

    @GameTest(templateName = MOD_ID + ":directions", batchId = "directions")
    public static void directionality(TestContext ctx) {
        final BlockPos MAIN_BLOCK = new BlockPos(2, 2, 2);
        final BlockPos DUST1 = MAIN_BLOCK.east();
        final BlockPos DUST2 = MAIN_BLOCK.west();
        final BlockPos DUST3 = MAIN_BLOCK.north();
        final BlockPos DUST4 = MAIN_BLOCK.south();

        ServerRedstoneBlock.currentState.on();

        ctx.runAtTick(FIRST_TICK, () -> {
            assertPower(ctx, DUST1, 15);
            assertPower(ctx, DUST2, 15);
            assertPower(ctx, DUST3, 15);
            assertPower(ctx, DUST4, 15);

            ServerRedstoneBlock.currentState.warning();
        });

        ctx.runAtTick(SECOND_TICK, () -> {
            assertPower(ctx, DUST1, 1);
            assertPower(ctx, DUST2, 1);
            assertPower(ctx, DUST3, 1);
            assertPower(ctx, DUST4, 1);

            ServerRedstoneBlock.currentState.off();
        });

        ctx.runAtTick(THIRD_TICK, () -> {
            assertPower(ctx, DUST1, 0);
            assertPower(ctx, DUST2, 0);
            assertPower(ctx, DUST3, 0);
            assertPower(ctx, DUST4, 0);

            ServerRedstoneBlock.currentState.on();
        });

        ctx.runAtTick(FOURTH_TICK, () -> {
            assertPower(ctx, DUST1, 15);
            assertPower(ctx, DUST2, 15);
            assertPower(ctx, DUST3, 15);
            assertPower(ctx, DUST4, 15);
            ServerRedstoneBlock.currentState.on();
            ctx.complete();
        });

    }

    @GameTest(templateName = MOD_ID+":"+"empty")
    public static void isRecipePresent(TestContext ctx) {
        RecipeManager recipeManager = ctx.getWorld().getServer().getRecipeManager();
        Optional<RecipeEntry<?>> recipe = recipeManager.get(MAIN_ID);
        ctx.assertTrue(recipe.isPresent(), String.format("Recipe is not present. Got %s", recipe));
        ctx.complete();
    }

    private static void assertPower(TestContext ctx, BlockPos pos, int expected) {
        Integer actual = ctx.getBlockState(pos).get(RedstoneWireBlock.POWER);
        ctx.assertTrue(actual == expected, String.format("Block at %s should have power %d but has %d", pos, expected, actual));
    }
}
