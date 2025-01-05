/*? if neoforge && <1.21.4 {*/
/*package gg.meza.serverredstoneblock.neoforge.datagen.pre1214;

import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static gg.meza.serverredstoneblock.neoforge.RegistryHelper.REDSTONE_BLOCK;

public class SRSRecipeProvider extends RecipeProvider {
    protected SRSRecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, REDSTONE_BLOCK.get())
                .pattern("L")
                .pattern("R")
                .input('L', Items.LEVER)
                .input('R', Items.REDSTONE_BLOCK)
                .criterion("has_redstone_block", RecipeUnlockedCriterion.create(Identifier.of("minecraft", "redstone_block")))
                .criterion("has_lever", RecipeUnlockedCriterion.create(Identifier.of("minecraft", "lever")))
                .offerTo(exporter);
    }
}
*//*?}*/
