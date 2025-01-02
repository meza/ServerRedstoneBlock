/*? if forge {*/
/*package gg.meza.serverredstoneblock.forge.datagen;

import gg.meza.serverredstoneblock.ServerRedstoneBlock;
import gg.meza.serverredstoneblock.forge.datagen.loot.BlockLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ServerRedstoneBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
//    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        DataOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<RegistryWrapper.WrapperLookup> lookupProvider = event.getLookupProvider();

//        generator.addProvider(event.includeServer(), new BlockTagProvider(output, lookupProvider, existingFileHelper));
//        generator.addProvider(event.includeServer(), BlockLootTableProvider.create(output/^? if >= 1.21 {^/, lookupProvider/^?}^/));
        generator.addProvider(event.includeServer(), new RecipeProvider(output/^? if >= 1.21 {^/, lookupProvider/^?}^/));

        /^? if >= 1.21.4 {^/
        generator.addProvider(event.includeClient(), new ModelProvider(output));
        /^?} else {^/
        /^generator.addProvider(event.includeClient(), new BlockStateProvider(output, existingFileHelper));
        ^//^?}^/
        generator.addProvider(event.includeClient(), new EnglishLanguageProvider(output));
    }

}
*//*?}*/
