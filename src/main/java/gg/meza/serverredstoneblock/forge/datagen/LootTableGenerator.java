/*? if forge {*/
/*package gg.meza.serverredstoneblock.forge.datagen;

import net.minecraft.block.Block;
/^? if >= 1.21.4 {^/
/^import net.minecraft.data.loottable.BlockLootTableGenerator;
^//^?} else {^/
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
/^?}^/

/^? if >= 1.21 {^/

import net.minecraft.registry.RegistryWrapper;
/^?}^/

import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

import static gg.meza.serverredstoneblock.forge.RegistryHelper.BLOCKS;
import static gg.meza.serverredstoneblock.forge.RegistryHelper.REDSTONE_BLOCK;


public class LootTableGenerator extends BlockLootTableGenerator {
    public LootTableGenerator(/^? if >= 1.21 {^/RegistryWrapper.WrapperLookup wrapperLookup/^?}^/) {
        super(Set.of(), FeatureFlags.DEFAULT_ENABLED_FEATURES/^? if >= 1.21 {^/, wrapperLookup/^?}^/);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get)::iterator;
    }

    @Override
    protected void generate() {
        this.addDrop(REDSTONE_BLOCK.get());
    }
}
*//*?}*/
