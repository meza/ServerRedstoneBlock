/*? if forge && >= 1.21.4 {*/
/*package gg.meza.serverredstoneblock.forge.datagen;


import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.data.DataOutput;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

import static gg.meza.serverredstoneblock.forge.RegistryHelper.BLOCKS;

public class ModelProvider extends net.minecraft.client.data.ModelProvider {
    public ModelProvider(DataOutput output) {
        super(output);
    }

    @Override
    protected Stream<Block> getKnownBlocks() {
        return BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get);
    }

    @Override
    protected BlockStateModelGenerator getBlockModelGenerators(BlockStateSuppliers blocks, ItemAssets items, ModelSuppliers models) {
        return new ModBlockStateModelGenerator(blocks, items, models);
    }
}
*//*?}*/
