package io.iikorni.etcher.block

import io.iikorni.etcher.screen.EtcherScreenHandler
import net.minecraft.block.AnvilBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.*
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class EtcherBlock(settings: Settings) : Block(settings) {

    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (!world.isClient) {
            var screenHandlerFactory = state.createScreenHandlerFactory(world, pos)

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory)
            }
        }
        return ActionResult.SUCCESS
    }

    override fun createScreenHandlerFactory(
        state: BlockState?,
        world: World?,
        pos: BlockPos?
    ): NamedScreenHandlerFactory? {
        return SimpleNamedScreenHandlerFactory({ syncId: Int, inventory: PlayerInventory, player: PlayerEntity ->
            EtcherScreenHandler(
                syncId,
                inventory,
                ScreenHandlerContext.create(world, pos)
            )
        }, Text.translatable("block.etcher.etcher"))
    }

    override fun hasComparatorOutput(state: BlockState): Boolean = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int =
        ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))
}