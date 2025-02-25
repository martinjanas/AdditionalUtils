package additional_utils.blocks.block;

import additional_utils.block_entities.block_entity.BlockEntityCrafter;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockCrafter extends Block implements EntityBlock
{
    public BlockCrafter(Properties properties)
    {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return BlockEntityRegistry.crafter.get().create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState block_state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (level.isClientSide())
            return InteractionResult.SUCCESS; // Return SUCCESS instead of FAIL

        if (player instanceof ServerPlayer serverPlayer)
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof BlockEntityCrafter crafter)
                serverPlayer.openMenu(crafter);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return type == BlockEntityRegistry.crafter.get() ? (lvl, pos, st, be) ->
        {
            if (be instanceof BlockEntityCrafter crafter)
                crafter.tick(lvl, pos, st, be);
        } : null;
    }
}
