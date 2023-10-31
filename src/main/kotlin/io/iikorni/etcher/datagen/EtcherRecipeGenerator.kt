package io.iikorni.etcher.datagen

import io.iikorni.etcher.init.EtcherBlocks
import io.iikorni.etcher.init.EtcherItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.tag.ItemTags
import java.util.function.Consumer

class EtcherRecipeGenerator(output: FabricDataOutput) : FabricRecipeProvider(output) {

    override fun generate(exporter: Consumer<RecipeJsonProvider>) {
        CookingRecipeJsonBuilder.createSmelting(
            Ingredient.fromTag(ItemTags.MUSIC_DISCS),
            RecipeCategory.MISC,
            EtcherItems.BLANK_DISC,
            0.45f,
            200)
            .criterion("has_music_disc", conditionsFromTag(ItemTags.MUSIC_DISCS))
            .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, EtcherBlocks.ETCHER_BLOCK).pattern("iww").pattern("wdw").pattern("www")
            .input('w', Ingredient.fromTag(ItemTags.PLANKS))
            .input('i', Items.IRON_INGOT)
            .input('d', Items.DIAMOND)
            .criterion("has_music_disc", conditionsFromTag(ItemTags.MUSIC_DISCS))
            .criterion("has_blank_disc", conditionsFromItem(EtcherItems.BLANK_DISC))
            .offerTo(exporter)
    }

}