/*? if fabric {*/
package gg.meza.serverredstoneblock.fabric.datagen;

import gg.meza.serverredstoneblock.fabric.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

/*? if >= 1.21 {*/
/*import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;
*//*?}*/

public class EnglishLanguageProvider extends FabricLanguageProvider {
    public EnglishLanguageProvider(FabricDataOutput dataOutput/*? if >= 1.21 {*//*, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup*//*?}*/) {
        super(dataOutput, "en_us"/*? if >= 1.21 {*//*, registryLookup*//*?}*/);
    }

    @Override
    public void generateTranslations(/*? if >= 1.21 {*//*RegistryWrapper.WrapperLookup registryLookup, *//*?}*/TranslationBuilder translationBuilder) {
        translationBuilder.add(RegistryHelper.REDSTONE_BLOCK, "Server Redstone Block");
    }
}
/*?}*/
