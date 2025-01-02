/*? if forge && < 1.21.4 {*/
/*package gg.meza.serverredstoneblock.forge.datagen;

//Only used in pre 1.21.4 versions as there is a lot of changes in 1.21.4

import gg.meza.serverredstoneblock.ServerPowerState;

import net.minecraftforge.common.data.ExistingFileHelper;

import net.minecraft.util.Identifier;
import net.minecraft.data.DataOutput;

import static gg.meza.serverredstoneblock.RedstoneBlock.POWER_STATE;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.blockName;
import static gg.meza.serverredstoneblock.forge.RegistryHelper.REDSTONE_BLOCK;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(DataOutput output, ExistingFileHelper exFileHelper) {
        super(output, MOD_ID, exFileHelper);
    }


    @Override
    protected void registerStatesAndModels() {
        String prefix = "block/";
        Identifier parentId = Identifier.of(MOD_ID, blockName);

        simpleBlockItem(REDSTONE_BLOCK.get(), models().withExistingParent(blockName, parentId.withPrefixedPath(prefix)));

        getVariantBuilder(REDSTONE_BLOCK.get()).partialState()
                .with(POWER_STATE, ServerPowerState.ON)
                .modelForState()
                .modelFile(cubeAll(REDSTONE_BLOCK.get()))
                .addModel()
                .partialState()
                .with(POWER_STATE, ServerPowerState.OFF)
                .modelForState()
                .modelFile(
                        models()
                                .withExistingParent(prefix + blockName + "_off", parentId)
                                .texture(
                                        "all",
                                        Identifier.of(MOD_ID, blockName + "_off").withPrefixedPath(prefix)
                                )
                )
                .addModel()
                .partialState()
                .with(POWER_STATE, ServerPowerState.WARNING)
                .modelForState()
                .modelFile(
                        models()
                                .withExistingParent(prefix + blockName + "_warning", parentId)
                                .texture(
                                        "all",
                                        Identifier.of(MOD_ID, blockName + "_warning").withPrefixedPath(prefix)
                                )
                )
                .addModel();
    }
}
*//*?}*/
