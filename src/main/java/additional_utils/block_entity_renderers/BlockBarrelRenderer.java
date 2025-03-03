package additional_utils.block_entity_renderers;

import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BlockBarrelRenderer implements BlockEntityRenderer<BlockEntityBarrel>
{
    public BlockBarrelRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(BlockEntityBarrel barrel, float pPartialTick, PoseStack matrix, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay)
    {
        ItemStack stack = barrel.getDisplayedItem();
        if (stack.isEmpty())
            return; // No item to display

        // Setup transformations
        matrix.pushPose();

        // Get block facing direction
        Direction facing = barrel.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        // Move item to front face of the block
        matrix.translate(0.5, 0.6, 0.75); // Default position (adjust as needed)

        // Rotate item based on facing direction
        switch (facing)
        {
            case NORTH -> matrix.translate(0, 0, -0.26);
            case SOUTH -> matrix.translate(0, 0, 0.26);
            case WEST -> matrix.translate(-0.26, 0, 0);
            case EAST -> matrix.translate(0.26, 0, 0);
        }

        // Scale down item so it fits on the block
        matrix.scale(0.5f, 0.5f, 0.5f);

        // Get Minecraft's item renderer
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GUI, LightTexture.FULL_BRIGHT, pPackedOverlay, matrix, pBuffer, barrel.getLevel(),0);

        matrix.popPose();
    }
}

