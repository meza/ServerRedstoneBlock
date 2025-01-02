/*? if forge {*/
/*package gg.meza.serverredstoneblock.forge.datagen;

import gg.meza.serverredstoneblock.forge.RegistryHelper;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

public class BlockTagProvider extends BlockTagsProvider {
    public BlockTagProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MOD_ID, existingFileHelper);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(RegistryHelper.REDSTONE_BLOCK.get());
    }
}
*//*?}*/
