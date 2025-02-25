package additional_utils.blocks.block;

import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;


public class BlockBarrel extends Block implements EntityBlock
{
    public BlockBarrel(Properties properties)
    {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return BlockEntityRegistry.barrel.get().create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState block_state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (level.isClientSide())
            return InteractionResult.SUCCESS;

        if (player instanceof ServerPlayer serverPlayer && player.isShiftKeyDown())
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof BlockEntityBarrel barrel)
                serverPlayer.openMenu(barrel);
        }


        return InteractionResult.CONSUME;
    }
}
