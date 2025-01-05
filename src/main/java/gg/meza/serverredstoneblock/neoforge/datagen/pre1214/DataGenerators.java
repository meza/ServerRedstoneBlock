/*? if neoforge && <1.21.4 {*/
/*package gg.meza.serverredstoneblock.neoforge.datagen.pre1214;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
         DataGenerator generator = event.getGenerator();
         DataOutput output = generator.getPackOutput();
         CompletableFuture<RegistryWrapper.WrapperLookup> lookupProvider = event.getLookupProvider();
         generator.addProvider(event.includeServer(), new SRSRecipeProvider(output, lookupProvider));
    }
}

*//*?}*/
