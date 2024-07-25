package io.iikorni.etcher.screen

import io.iikorni.etcher.Etcher
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.AnvilScreen
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.StringVisitable
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class EtcherScreen(handler: EtcherScreenHandler,
                   val playerInventory: PlayerInventory, title: Text
) : HandledScreen<EtcherScreenHandler>(handler, playerInventory, title) {
    val TEXTURE = Identifier(Etcher.MOD_ID, "textures/gui/container/etcher.png")

    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        val x = (width - backgroundWidth) / 2;
        val y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight)

        if ((handler.getSlot(0).hasStack() || handler.getSlot(1).hasStack()) && !handler.getSlot(2).hasStack()) {
            context.drawTexture(TEXTURE, x + 71, y + 32, backgroundWidth, 0, 28, 21)
        }
    }

    override fun drawForeground(context: DrawContext, mouseX: Int, mouseY: Int) {
        super.drawForeground(context, mouseX, mouseY)
        var text: Text? = null
        var color: Int = SUCCESS_COLOUR


        if (handler.getSlot(2).hasStack()) {
            text = Text.translatable("container.etcher.cost", handler.levelCost.get())
            if (!handler.getSlot(2).canTakeItems(this.playerInventory.player)) {
                color = FAILURE_COLOUR
            }
        }

        if (text != null) {
            val k = backgroundWidth - 8 - textRenderer.getWidth(text as StringVisitable?) - 2;
            context.fill(k - 2, 67, backgroundWidth - 8, 79, BACK_COLOUR)
            context.drawTextWithShadow(textRenderer, text, k, 69, color)
        }
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(context, mouseX, mouseY, delta)
        super.render(context, mouseX, mouseY, delta)
        drawMouseoverTooltip(context, mouseX, mouseY)
    }

    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    companion object {
        const val BACK_COLOUR = 0x4F000000
        const val SUCCESS_COLOUR = 0x80ff20
        const val FAILURE_COLOUR = 0xff6060
    }
}