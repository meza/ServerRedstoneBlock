/*? if forge {*/
/*package gg.meza.serverredstoneblock.forge.datagen;

import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
/^? if >= 1.21.4 {^/
/^import net.minecraft.data.DataOutput;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
^//^?} else {^/
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.DataOutput;
/^?}^/

import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

/^? if >= 1.21 {^/
import net.minecraft.registry.RegistryWrapper;
/^?}^/

import java.util.concurrent.CompletableFuture;

import static gg.meza.serverredstoneblock.forge.RegistryHelper.REDSTONE_BLOCK;


/^? if >= 1.21.4 {^/
/^public class RecipeProvider extends RecipeGenerator.RecipeProvider implements IConditionBuilder {
    protected RecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    ^//^?} else {^/
public class RecipeProvider extends net.minecraft.data.server.recipe.RecipeProvider implements IConditionBuilder {
    public RecipeProvider(DataOutput output/^? if >= 1.21 {^/, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture/^?}^/) {
        super(output/^? if >= 1.21 {^/, registryLookupFuture/^?}^/);
    }
    /^?}^/

    /^? if >=1.21.4 {^/
    /^@Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            @Override
            public void generate() {
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
        return "";
    }
    ^//^?} else {^/
    @Override
    protected void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, REDSTONE_BLOCK.get())
                .pattern("L")
                .pattern("R")
                .input('L', Items.LEVER)
                .input('R', Items.REDSTONE_BLOCK)
                .criterion("has_redstone_block", RecipeUnlockedCriterion.create(Identifier.of("minecraft", "redstone_block")))
                .criterion("has_lever", RecipeUnlockedCriterion.create(Identifier.of("minecraft", "lever")))
                .offerTo(exporter);
    }
    /^?}^/
}
*//*?}*/
