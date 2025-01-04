/*? if neoforge && >= 1.21.4 {*/
/*package gg.meza.serverredstoneblock.neoforge.datagen;

import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.DataOutput;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static gg.meza.serverredstoneblock.neoforge.RegistryHelper.REDSTONE_BLOCK;

public class SRSRecipeProvider extends RecipeGenerator.RecipeProvider {
    protected SRSRecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {

            @Override
            protected void generate() {
                RegistryKey<Recipe<?>> redstoneBlockRecipe = RegistryKey.of(RegistryKeys.RECIPE, Identifier.of("redstone_block"));
                RegistryKey<Recipe<?>> leverRecipe = RegistryKey.of(RegistryKeys.RECIPE, Identifier.of("lever"));

                createShaped(RecipeCategory.REDSTONE, REDSTONE_BLOCK.get())
                        .pattern("L")
                        .pattern("R")
                        .input('L', Items.LEVER)
                        .input('R', Items.REDSTONE_BLOCK)
                        .criterion("has_redstone_block", RecipeUnlockedCriterion.create(redstoneBlockRecipe))
                        .criterion("has_lever", RecipeUnlockedCriterion.create(leverRecipe))
                        .offerTo(exporter);
            }
        };
    }


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
*//*?}*/
