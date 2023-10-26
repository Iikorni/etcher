package io.iikorni.etcher

import io.iikorni.etcher.datagen.EtcherModelGenerator
import io.iikorni.etcher.datagen.EtcherRecipeGenerator
import io.iikorni.etcher.datagen.EtcherLootTableGenerator
import io.iikorni.etcher.datagen.EtcherBlockTagGenerator
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object EtcherDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()

		pack.addProvider(::EtcherModelGenerator)
		pack.addProvider(::EtcherRecipeGenerator)
		pack.addProvider(::EtcherLootTableGenerator)
		pack.addProvider(::EtcherBlockTagGenerator)
	}


}