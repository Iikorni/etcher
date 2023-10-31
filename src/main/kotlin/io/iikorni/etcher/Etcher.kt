package io.iikorni.etcher

import io.iikorni.etcher.init.EtcherBlocks
import io.iikorni.etcher.init.EtcherEntities
import io.iikorni.etcher.init.EtcherItemGroups
import io.iikorni.etcher.init.EtcherItems
import io.iikorni.etcher.recipe.DiscCloningRecipe
import io.iikorni.etcher.recipe.DiscCloningRecipeSerializer
import io.iikorni.etcher.recipe.EtchingRecipe
import io.iikorni.etcher.recipe.EtchingRecipeSerializer
import net.fabricmc.api.ModInitializer
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object Etcher : ModInitializer {
	val MOD_ID: String = "etcher"

	private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		EtcherBlocks.init(Registries.BLOCK)
		EtcherItems.init(Registries.ITEM)
		EtcherBlocks.initItems(Registries.ITEM)
		EtcherEntities.initBlockEntities(Registries.BLOCK_ENTITY_TYPE)
		EtcherItemGroups.init(Registries.ITEM_GROUP)
		Registry.register(Registries.RECIPE_SERIALIZER, EtchingRecipeSerializer.ID, EtchingRecipeSerializer.INSTANCE)
		Registry.register(Registries.RECIPE_TYPE, Identifier(MOD_ID, EtchingRecipe.Type.ID), EtchingRecipe.Type.INSTANCE)
		Registry.register(Registries.RECIPE_SERIALIZER, DiscCloningRecipeSerializer.ID, DiscCloningRecipeSerializer.INSTANCE)
		Registry.register(Registries.RECIPE_TYPE, Identifier(MOD_ID, DiscCloningRecipe.Type.ID), DiscCloningRecipe.Type.INSTANCE)
	}
}