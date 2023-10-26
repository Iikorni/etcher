package io.iikorni.etcher.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.iikorni.etcher.Etcher
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

class EtchingRecipeSerializer private constructor(): RecipeSerializer<EtchingRecipe> {

    private val codec: Codec<EtchingRecipe> = RecordCodecBuilder.create { instance ->
        val product = instance.group(
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("label").forGetter { recipe -> recipe.label },
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("base").forGetter  { recipe -> recipe.base },
            Codec.INT.fieldOf("levels_required").forGetter { recipe -> recipe.levelsRequired },
            Registries.ITEM.codec.xmap(::ItemStack, ItemStack::getItem).fieldOf("result").forGetter { recipe -> recipe.result}
        )
        product.apply(instance, ::EtchingRecipe)
    }
    override fun codec(): Codec<EtchingRecipe> = codec

    override fun read(buf: PacketByteBuf): EtchingRecipe {
        val label = Ingredient.fromPacket(buf)
        val base = Ingredient.fromPacket(buf)
        val levelsRequired = buf.readInt()
        val result = buf.readItemStack()
        return EtchingRecipe(label, base, levelsRequired, result)
    }

    override fun write(buf: PacketByteBuf, recipe: EtchingRecipe) {
        recipe.label.write(buf)
        recipe.base.write(buf)
        buf.writeInt(recipe.levelsRequired)
        buf.writeItemStack(recipe.result)
    }

    companion object {
        val INSTANCE = EtchingRecipeSerializer()
        val ID = Identifier(Etcher.MOD_ID, "etching")
    }
}