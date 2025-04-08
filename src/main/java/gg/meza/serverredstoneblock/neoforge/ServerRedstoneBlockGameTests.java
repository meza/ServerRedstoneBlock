/*? if neoforge && >=1.21.5 {*/
package gg.meza.serverredstoneblock.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

public final class ServerRedstoneBlockGameTests {
    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MOD_ID)
    public static final class Register {
        @SubscribeEvent
        public static void registerGameTest(RegisterGameTestsEvent event) {

//            event.registerTest(Identifier.of(MOD_ID, "redstone_block_test"), new E2ETests(new TestData<>(
//                    null,
//                    Identifier.of(MOD_ID, "empty"),
//                    200,
//                    200,
//                    true,
//                    BlockRotation.NONE
//            )));
        }
    }
}

/*?}*/
