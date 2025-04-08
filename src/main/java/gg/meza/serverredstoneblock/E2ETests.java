package gg.meza.serverredstoneblock;

/*? if fabric {*/

/*/^? if >=1.21.5 {^/
/^import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.text.Text;
^//^?}^/

/^? if <1.21.5 {^/
import net.minecraft.test.GameTest;
import static net.fabricmc.fabric.api.gametest.v1.FabricGameTest.EMPTY_STRUCTURE;
/^?}^/

import static gg.meza.serverredstoneblock.fabric.RegistryHelper.REDSTONE_BLOCK;

*//*?}*/


/*? if neoforge {*/

/*? if >=1.21.5 {*/

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import gg.meza.serverredstoneblock.neoforge.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.test.TestContext;
import net.minecraft.test.TestData;
import net.minecraft.test.TestEnvironmentDefinition;
import net.minecraft.test.TestInstance;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.LOGGER;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MAIN_ID;


/*? if forge {*/
/*import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

@GameTestHolder(MOD_ID)
*//*?}*/
/*? if neoforge && <1.21.5 {*/
/*@PrefixGameTestTemplate(value = false)
 *//*?}*/
public class E2ETests /*? if neoforge && >=1.21.5 {*/extends TestInstance/*?}*/ {
    public static final int FIRST_TICK = 40;
    public static final int SECOND_TICK = 60;
    public static final int THIRD_TICK = 80;
    public static final int FOURTH_TICK = 100;

    /*? if fabric && <1.21.5 {*/
    /*public static final String template = EMPTY_STRUCTURE;
     *//*?}*/
    /*? if forge {*/
    /*public static final String template = MOD_ID + ":empty";
     *//*?}*/
    /*? if neoforge {*/
    public static final String template = "empty";
    /*?}*/

    /*? if neoforge && >=1.21.5 {*/

    public E2ETests(TestData<RegistryEntry<TestEnvironmentDefinition>> data) {
        super(data);
        LOGGER.info("Creating E2ETests");
    }

    public static final MapCodec<E2ETests> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    TestData.CODEC.forGetter(E2ETests::getData)
            ).apply(instance, E2ETests::new)
    );

    @Override
    public void start(TestContext context) {
        LOGGER.info("Starting E2ETests");
        stateTest(context);
        directionality(context);
        isRecipePresent(context);
        multiBlockStateTest(context);

        LOGGER.info("Finished starting E2ETests");
        context.complete();
    }

    @Override
    public MapCodec<? extends TestInstance> getCodec() {
        return CODEC;
    }

    @Override
    protected MutableText getTypeDescription() {
        return Text.literal("E2ETests");
    }
    /*?}*/


    /*? if forgeLike {*/
    /*? if forge {*/
    /*@Mod.EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
     *//*?}*/
    /*? if neoforge && <1.21.5 {*/
    /*@EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    *//*?}*/
    /*? if <1.21.5 {*/
    /*public static final class Register {
        private static RegisterGameTestsEvent event;

        @SubscribeEvent
        public static void registerGameTest(RegisterGameTestsEvent event) {
            Register.event = event;
            LOGGER.info("Registering game tests");
            event.register(E2ETests.class);
        }
    }
    *//*?}*/
    /*?}*/

    private static void placeBlock(TestContext ctx, BlockPos blockPos, Block block) {
        ctx.setBlockState(blockPos.down(), Blocks.DIAMOND_BLOCK.getDefaultState());
        ctx.setBlockState(blockPos, block.getDefaultState());
        block.onPlaced(ctx.getWorld(), ctx.getAbsolutePos(blockPos), ctx.getBlockState(blockPos), null, null);
    }

    private static void startTicking(TestContext ctx, BlockPos blockPos) {
        ctx.waitAndRun(1, () -> {
            /*? if forgeLike {*/
            Block testBlock = RegistryHelper.REDSTONE_BLOCK.get();
             /*?}*/

            /*? if fabric {*/
            /*Block testBlock = REDSTONE_BLOCK;
            *//*?}*/
            placeBlock(ctx, blockPos, testBlock);
        });
    }

    /*? if !(neoforge && 1.21.5) {*/
    /*@GameTest(
            /^? if neoforge {^/templateNamespace = MOD_ID,/^?}^/
            /^? if >1.21.4 {^/maxTicks = 200/^?}^/
            /^? if <1.21.5 {^/
            /^templateName = template,
            batchId = "state-test",
            tickLimit = 200
            ^//^?}^/
    )
    *//*?}*/
    public /*? if <1.21.5 {*//*static*//*?}*/ void stateTest(TestContext ctx) {
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
            /*? if neoforge && =1.21.5 {*/
            /*?} else {*/
            /*ctx.complete();
            *//*?}*/
        });
    }

    /*? if !(neoforge && 1.21.5) {*/
    /*@GameTest(
            /^? if neoforge {^/templateNamespace = MOD_ID,/^?}^/
            /^? if >1.21.4 {^/maxTicks = 200/^?}^/
            /^? if <1.21.5 {^/
            /^templateName = template,
            batchId = "multi-block",
            tickLimit = 200
            ^//^?}^/
    )
    *//*?}*/
    public /*? if <1.21.5 {*//*static*//*?}*/ void multiBlockStateTest(TestContext ctx) {
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
            /*? if neoforge && =1.21.5 {*/
            /*?} else {*/
            /*ctx.complete();
            *//*?}*/
        });

    }

    /*? if !(neoforge && 1.21.5) {*/
    /*@GameTest(
            /^? if neoforge {^/templateNamespace = MOD_ID,/^?}^/
            /^? if >1.21.4 {^/maxTicks = 200/^?}^/
            /^? if <1.21.5 {^/
            /^templateName = template,
            batchId = "directions",
            tickLimit = 200
            ^//^?}^/
    )
    *//*?}*/
    public /*? if <1.21.5 {*//*static*//*?}*/ void directionality(TestContext ctx) {
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
            /*? if neoforge && =1.21.5 {*/
            /*?} else {*/
            /*ctx.complete();
            *//*?}*/

        });

    }

    /*? if !(neoforge && 1.21.5) {*/
    /*@GameTest(/^? if neoforge {^/templateNamespace = MOD_ID,/^?}^/
            /^? if <1.21.5 {^//^templateName = template^//^?}^/
    )
    *//*?}*/
    public /*? if <1.21.5 {*//*static*//*?}*/ void isRecipePresent(TestContext ctx) {

        /*? if >= 1.21.2 {*/
        RegistryKey<Recipe<?>> recipeKey = RegistryKey.of(RegistryKeys.RECIPE, MAIN_ID);
        Optional<RecipeEntry<?>> recipe = ctx.getWorld().getServer().getRecipeManager().get(recipeKey);
        /*?} else {*/
        /*RecipeManager recipeManager = ctx.getWorld().getServer().getRecipeManager();
        Optional<RecipeEntry<?>> recipe = recipeManager.get(MAIN_ID);
        *//*?}*/
        /*? if <1.21.5 {*/
        /*ctx.assertTrue(recipe.isPresent(), String.format("Recipe is not present. Got %s", recipe));
        *//*?} else {*/
        ctx.assertTrue(recipe.isPresent(), Text.of("Recipe is not present."));
        /*?}*/
        /*? if neoforge && =1.21.5 {*/
        /*?} else {*/
        /*ctx.complete();
        *//*?}*/
    }

    private static void assertPower(TestContext ctx, BlockPos pos, int expected) {
        Integer actual = ctx.getBlockState(pos).get(RedstoneWireBlock.POWER);
        /*? if <1.21.5 {*/
        /*ctx.assertTrue(actual == expected, String.format("Block at %s should have power %d but has %d", pos, expected, actual));
        *//*?} else {*/
        ctx.assertTrue(actual == expected, Text.of(String.format("Block at %s should have power %d but has %d", pos, expected, actual)));
        /*?}*/
    }
}
