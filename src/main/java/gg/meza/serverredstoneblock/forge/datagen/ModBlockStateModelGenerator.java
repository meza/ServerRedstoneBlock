/*? if forge && >= 1.21.4 {*/
/*package gg.meza.serverredstoneblock.forge.datagen;

import gg.meza.serverredstoneblock.forge.RegistryHelper;
import net.minecraft.client.data.*;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.blockName;

public class ModBlockStateModelGenerator extends BlockStateModelGenerator {
    public ModBlockStateModelGenerator(Consumer<BlockStateSupplier> blockStateCollector, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelSupplier> modelCollector) {
        super(blockStateCollector, itemModelOutput, modelCollector);
    }

    @Override
    public void register() {
        String prefix = "block/";
        Identifier parentId = Identifier.of(MOD_ID, blockName);

        this.registerParentedItemModel(
                RegistryHelper.REDSTONE_BLOCK.get(),
                parentId
        );
//        simpleBlockItem(REDSTONE_BLOCK.get(), models().withExistingParent(blockName, parentId.withPrefixedPath(prefix)));
//
//        getVariantBuilder(REDSTONE_BLOCK.get()).partialState()
//                .with(POWER_STATE, ServerPowerState.ON)
//                .modelForState()
//                .modelFile(cubeAll(REDSTONE_BLOCK.get()))
//                .addModel()
//                .partialState()
//                .with(POWER_STATE, ServerPowerState.OFF)
//                .modelForState()
//                .modelFile(
//                        models()
//                                .withExistingParent(prefix + blockName + "_off", parentId)
//                                .texture(
//                                        "all",
//                                        Identifier.of(MOD_ID, blockName + "_off").withPrefixedPath(prefix)
//                                )
//                )
//                .addModel()
//                .partialState()
//                .with(POWER_STATE, ServerPowerState.WARNING)
//                .modelForState()
//                .modelFile(
//                        models()
//                                .withExistingParent(prefix + blockName + "_warning", parentId)
//                                .texture(
//                                        "all",
//                                        Identifier.of(MOD_ID, blockName + "_warning").withPrefixedPath(prefix)
//                                )
//                )
//                .addModel();
    }
}
*//*?}*/
