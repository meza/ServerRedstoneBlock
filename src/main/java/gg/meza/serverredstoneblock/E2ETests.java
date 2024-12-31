package gg.meza.serverredstoneblock;

/*? if fabric {*/
/*import gg.meza.serverredstoneblock.fabric.RegistryHelper;
 *//*?}*/

/*? if forge {*/

import gg.meza.serverredstoneblock.forge.RegistryHelper;
/*?}*/

import net.minecraft.block.*;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

/*? if forge {*/
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.GameTestHolder;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

@GameTestHolder(MOD_ID)
/*?}*/
public class E2ETests {
    public static final int FIRST_TICK = 40;
    public static final int SECOND_TICK = 60;
    public static final int THIRD_TICK = 80;
    public static final int FOURTH_TICK = 100;

    /*? if forge {*/
    @Mod.EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Register {
        @SubscribeEvent
        public static void registerGameTest(RegisterGameTestsEvent event) {
            LOGGER.info("Registering game tests");
            event.register(E2ETests.class);
        }
    }
    /*?}*/

    private static void startTicking(TestContext ctx, BlockPos blockPos) {
        ctx.waitAndRun(1, () -> {
            /*? if forge {*/
            Block testBlock = RegistryHelper.REDSTONE_BLOCK.get();
            /*?}*/

            /*? if fabric {*/
            /*Block testBlock = RegistryHelper.REDSTONE_BLOCK;
             *//*?}*/
            testBlock.onPlaced(ctx.getWorld(), ctx.getAbsolutePos(blockPos), ctx.getBlockState(blockPos), null, null);
        });
    }

    @GameTest(templateName = MOD_ID + ":state-test", batchId = "state-test", tickLimit = 200)
    public static void stateTest(TestContext ctx) {
        BlockPos MAIN_REDSTONE = new BlockPos(2, 2, 2);
        ServerRedstoneBlock.currentState.on();
        BlockPos blockPos = new BlockPos(1, 2, 2);

        startTicking(ctx, blockPos);

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

    @GameTest(templateName = MOD_ID + ":multi-block", batchId = "multi-block", tickLimit = 200)
    public static void multiBlockStateTest(TestContext ctx) {
        ServerRedstoneBlock.currentState.on();
        BlockPos first = new BlockPos(0, 2, 0);
        BlockPos second = new BlockPos(0, 2, 2);
        BlockPos third = new BlockPos(0, 2, 4);

        startTicking(ctx, first);
        startTicking(ctx, second);
        startTicking(ctx, third);

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

    @GameTest(templateName = MOD_ID + ":directions", batchId = "directions", tickLimit = 200)
    public static void directionality(TestContext ctx) {
        final BlockPos MAIN_BLOCK = new BlockPos(2, 2, 2);
        final BlockPos DUST1 = MAIN_BLOCK.east();
        final BlockPos DUST2 = MAIN_BLOCK.west();
        final BlockPos DUST3 = MAIN_BLOCK.north();
        final BlockPos DUST4 = MAIN_BLOCK.south();
        ServerRedstoneBlock.currentState.on();

        startTicking(ctx, MAIN_BLOCK);

        ctx.runAtTick(FIRST_TICK, () -> {
            LOGGER.info("First tick");
            assertPower(ctx, DUST1, 15);
            assertPower(ctx, DUST2, 15);
            assertPower(ctx, DUST3, 15);
            assertPower(ctx, DUST4, 15);

            ServerRedstoneBlock.currentState.warning();
        });

        ctx.runAtTick(SECOND_TICK, () -> {
            LOGGER.info("Second tick");
            assertPower(ctx, DUST1, 1);
            assertPower(ctx, DUST2, 1);
            assertPower(ctx, DUST3, 1);
            assertPower(ctx, DUST4, 1);

            ServerRedstoneBlock.currentState.off();
        });

        ctx.runAtTick(THIRD_TICK, () -> {
            LOGGER.info("Third tick");
            assertPower(ctx, DUST1, 0);
            assertPower(ctx, DUST2, 0);
            assertPower(ctx, DUST3, 0);
            assertPower(ctx, DUST4, 0);

            ServerRedstoneBlock.currentState.on();
        });

        ctx.runAtTick(FOURTH_TICK, () -> {
            LOGGER.info("Fourth tick");
            assertPower(ctx, DUST1, 15);
            assertPower(ctx, DUST2, 15);
            assertPower(ctx, DUST3, 15);
            assertPower(ctx, DUST4, 15);
            ServerRedstoneBlock.currentState.on();
            LOGGER.info("Completing test");
            ctx.complete();
        });

    }

    @GameTest(templateName = MOD_ID + ":" + "empty")
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
