/*? if fabric {*/
/*package gg.meza.serverredstoneblock.fabric.datagen;

import com.google.gson.JsonObject;
import gg.meza.serverredstoneblock.ServerPowerState;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
/^? if >=1.21.4 {^/
/^import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.data.*;
^//^?} else {^/
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
/^?}^/

import net.minecraft.util.Identifier;

import static gg.meza.serverredstoneblock.RedstoneBlock.POWER_STATE;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;
import static gg.meza.serverredstoneblock.fabric.RegistryHelper.REDSTONE_BLOCK;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        Identifier mainBlockId = Identifier.of(MOD_ID, blockName).withPrefixedPath("block/");
        Identifier offBlockId = Identifier.of(MOD_ID, blockName+"_off").withPrefixedPath("block/");
        Identifier warningBlockId = Identifier.of(MOD_ID, blockName+"_warning").withPrefixedPath("block/");

        BlockStateVariantMap.SingleProperty<ServerPowerState> variantMap = BlockStateVariantMap.create(POWER_STATE);

        variantMap.register((powerState) -> {
            return switch (powerState) {
                case ON -> BlockStateVariant.create().put(VariantSettings.MODEL, mainBlockId);
                case OFF -> BlockStateVariant.create().put(VariantSettings.MODEL, offBlockId);
                case WARNING -> BlockStateVariant.create().put(VariantSettings.MODEL, warningBlockId);
            };
        });

        VariantsBlockStateSupplier variantSupplier = VariantsBlockStateSupplier.create(REDSTONE_BLOCK).coordinate(variantMap);
        blockStateModelGenerator.blockStateCollector.accept(variantSupplier);

        addModelTextureFor(blockStateModelGenerator, mainBlockId, Identifier.of("minecraft", "block/cube_all"));
        addModelTextureFor(blockStateModelGenerator, offBlockId, mainBlockId);
        addModelTextureFor(blockStateModelGenerator, warningBlockId, mainBlockId);
    }

    private void addModelTextureFor(BlockStateModelGenerator blockStateModelGenerator, Identifier blockId, Identifier parentId) {
        blockStateModelGenerator.modelCollector.accept(blockId,
                () -> {
                    var element =  new SimpleModelSupplier(parentId).get().getAsJsonObject();
                    var textures = new JsonObject();
                    textures.addProperty("all", blockId.toString());
                    element.add("textures", textures);
                    return element;
                }
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}

*//*?}*/
