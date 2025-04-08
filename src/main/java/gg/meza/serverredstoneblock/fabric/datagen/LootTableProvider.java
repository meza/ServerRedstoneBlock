/*? if fabric {*/
/*package gg.meza.serverredstoneblock.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
/^? if >= 1.21 {^/
/^import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;
^//^?}^/

import static gg.meza.serverredstoneblock.fabric.RegistryHelper.REDSTONE_BLOCK;

public class LootTableProvider extends FabricBlockLootTableProvider {
    public LootTableProvider(FabricDataOutput dataOutput/^? if >= 1.21 {^//^, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup^//^?}^/) {
        super(dataOutput/^? if >= 1.21 {^//^, registryLookup^//^?}^/);
    }

    @Override
    public void generate() {
        addDrop(REDSTONE_BLOCK);
    }
}
*//*?}*/
