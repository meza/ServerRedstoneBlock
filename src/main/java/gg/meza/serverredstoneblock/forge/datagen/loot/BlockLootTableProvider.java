/*? if forge {*/
/*package gg.meza.serverredstoneblock.forge.datagen.loot;

import gg.meza.serverredstoneblock.forge.datagen.LootTableGenerator;
import net.minecraft.data.DataOutput;
/^? if >= 1.21.4 {^/
/^import net.minecraft.data.loottable.LootTableProvider;
^//^?} else {^/
import net.minecraft.data.server.loottable.LootTableProvider;
/^?}^/

import net.minecraft.loot.context.LootContextTypes;
/^? if >= 1.21 {^/
/^import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;
^//^?}^/

import java.util.List;
import java.util.Set;

public class BlockLootTableProvider {
    public static LootTableProvider create(DataOutput output/^? if >= 1.21 {^//^, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture^//^?}^/) {
        return new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.LootTypeGenerator(LootTableGenerator::new, LootContextTypes.BLOCK)
            )/^? if >= 1.21 {^//^, registryLookupFuture^//^?}^/
        );
    }
}
*//*?}*/
