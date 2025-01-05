package gg.meza.serverredstoneblock;

/*? if forge {*/
/*import gg.meza.serverredstoneblock.forge.RegistryHelper;
 *//*?}*/

/*? if neoforge {*/

/*import gg.meza.serverredstoneblock.neoforge.RegistryHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
*//*?}*/

/*? if fabric {*/
import static gg.meza.serverredstoneblock.fabric.RegistryHelper.REDSTONE_BLOCK;
import static net.fabricmc.fabric.api.gametest.v1.FabricGameTest.EMPTY_STRUCTURE;
/*?}*/

import net.minecraft.block.*;
/*? if >=1.21.2 {*/
/*import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

*//*?}*/
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;


import java.util.Optional;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

/*? if forge {*/
/*import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.GameTestHolder;
*//*?}*/

/*? if forge {*/
/*import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

@GameTestHolder(MOD_ID)
*//*?}*/
/*? if neoforge {*/
/*@PrefixGameTestTemplate(value = false)
*//*?}*/
public class E2ETests {
    public static final int FIRST_TICK = 40;
    public static final int SECOND_TICK = 60;
    public static final int THIRD_TICK = 80;
    public static final int FOURTH_TICK = 100;

    /*? if fabric {*/
    public static final String template = EMPTY_STRUCTURE;
    /*?}*/
    /*? if forge {*/
    /*public static final String template = MOD_ID + ":empty";
    *//*?}*/
    /*? if neoforge {*/
    /*public static final String template = "empty";
    *//*?}*/

    /*? if forgeLike {*/
    /*/^? if forge {^/
    /^@Mod.EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
     ^//^?}^/
    /^? if neoforge {^/
    /^@EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    ^//^?}^/
    public static final class Register {
        @SubscribeEvent
        public static void registerGameTest(RegisterGameTestsEvent event) {
            LOGGER.info("Registering game tests");
            event.register(E2ETests.class);
        }
    }
    *//*?}*/

    private static void placeBlock(TestContext ctx, BlockPos blockPos, Block block) {
        ctx.setBlockState(blockPos.down(), Blocks.DIAMOND_BLOCK.getDefaultState());
        ctx.setBlockState(blockPos, block.getDefaultState());
        block.onPlaced(ctx.getWorld(), ctx.getAbsolutePos(blockPos), ctx.getBlockState(blockPos), null, null);
    }

    private static void startTicking(TestContext ctx, BlockPos blockPos) {
        ctx.waitAndRun(1, () -> {
            /*? if forgeLike {*/
            /*Block testBlock = RegistryHelper.REDSTONE_BLOCK.get();
            *//*?}*/

            /*? if fabric {*/
            Block testBlock = REDSTONE_BLOCK;
             /*?}*/
            placeBlock(ctx, blockPos, testBlock);
        });
    }

    @GameTest(
            /*? if neoforge {*//*templateNamespace = MOD_ID,*//*?}*/
            templateName = template,
            batchId = "state-test", tickLimit = 200
    )
    public static void stateTest(TestContext ctx) {
        BlockPos MAIN_REDSTONE = new BlockPos(2, 2, 2);
        ServerRedstoneBlock.currentState.on();
        BlockPos blockPos = new BlockPos(1, 2, 2);

        placeBlock(ctx, MAIN_REDSTONE, Blocks.REDSTONE_WIRE);

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

    @GameTest(
            /*? if neoforge {*//*templateNamespace = MOD_ID,*//*?}*/
            templateName = template, batchId = "multi-block", tickLimit = 200)
    public static void multiBlockStateTest(TestContext ctx) {
        ServerRedstoneBlock.currentState.on();
        final BlockPos first = new BlockPos(0, 2, 0);
        final BlockPos second = new BlockPos(0, 2, 2);
        final BlockPos third = new BlockPos(0, 2, 4);
        final BlockPos DUST1 = new BlockPos(1, 2, 0);
        final BlockPos DUST2 = new BlockPos(1, 2, 2);
        final BlockPos DUST3 = new BlockPos(1, 2, 4);

        placeBlock(ctx, DUST1, Blocks.REDSTONE_WIRE);
        placeBlock(ctx, DUST2, Blocks.REDSTONE_WIRE);
        placeBlock(ctx, DUST3, Blocks.REDSTONE_WIRE);


        startTicking(ctx, first);
        startTicking(ctx, second);
        startTicking(ctx, third);



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

    @GameTest(
            /*? if neoforge {*//*templateNamespace = MOD_ID,*//*?}*/
            templateName = template, batchId = "directions", tickLimit = 200)
    public static void directionality(TestContext ctx) {
        final BlockPos MAIN_BLOCK = new BlockPos(2, 2, 2);
        final BlockPos DUST1 = MAIN_BLOCK.east();
        final BlockPos DUST2 = MAIN_BLOCK.west();
        final BlockPos DUST3 = MAIN_BLOCK.north();
        final BlockPos DUST4 = MAIN_BLOCK.south();
        ServerRedstoneBlock.currentState.on();

        placeBlock(ctx, DUST1, Blocks.REDSTONE_WIRE);
        placeBlock(ctx, DUST2, Blocks.REDSTONE_WIRE);
        placeBlock(ctx, DUST3, Blocks.REDSTONE_WIRE);
        placeBlock(ctx, DUST4, Blocks.REDSTONE_WIRE);

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

    @GameTest(/*? if neoforge {*//*templateNamespace = MOD_ID,*//*?}*/
            templateName = template)
    public static void isRecipePresent(TestContext ctx) {

        /*? if >= 1.21.2 {*/
        /*RegistryKey<Recipe<?>> recipeKey = RegistryKey.of(RegistryKeys.RECIPE, MAIN_ID);
        Optional<RecipeEntry<?>> recipe = ctx.getWorld().getServer().getRecipeManager().get(recipeKey);
        *//*?} else {*/
        RecipeManager recipeManager = ctx.getWorld().getServer().getRecipeManager();
        Optional<RecipeEntry<?>> recipe = recipeManager.get(MAIN_ID);
        /*?}*/
        ctx.assertTrue(recipe.isPresent(), String.format("Recipe is not present. Got %s", recipe));
        ctx.complete();
    }

    private static void assertPower(TestContext ctx, BlockPos pos, int expected) {
        Integer actual = ctx.getBlockState(pos).get(RedstoneWireBlock.POWER);
        ctx.assertTrue(actual == expected, String.format("Block at %s should have power %d but has %d", pos, expected, actual));
    }
}
