/*? if fabric {*/
package gg.meza.serverredstoneblock.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;

/*? if >=1.21.4 {*/
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.recipe.Recipe;
/*?} else {*/
/*import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
*//*?}*/

/*? if >= 1.21 {*/
import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;
/*?}*/

import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;


import static gg.meza.serverredstoneblock.fabric.RegistryHelper.REDSTONE_BLOCK;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output/*? if >= 1.21 {*/, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture/*?}*/) {
        super(output/*? if >= 1.21 {*/, registriesFuture/*?}*/);
    }

    /*? if >=1.21.4 {*/
    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                RegistryKey<Recipe<?>> redstoneBlockRecipe = RegistryKey.of(RegistryKeys.RECIPE, Identifier.of("redstone_block"));
                RegistryKey<Recipe<?>> leverRecipe = RegistryKey.of(RegistryKeys.RECIPE, Identifier.of("lever"));

                createShaped(RecipeCategory.REDSTONE, REDSTONE_BLOCK)
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
        return "Server Redstone Block Recipes";
    }

    /*?} else {*/
    /*@Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, REDSTONE_BLOCK)
                .pattern("L")
                .pattern("R")
                .input('L', Items.LEVER)
                .input('R', Items.REDSTONE_BLOCK)
                .criterion("has_redstone_block", RecipeUnlockedCriterion.create(Identifier.of("minecraft", "redstone_block")))
                .criterion("has_lever", RecipeUnlockedCriterion.create(Identifier.of("minecraft", "lever")))
                .offerTo(exporter);
    }
    *//*?}*/
}

/*?}*/
