package additional_utils.blocks.block;

import additional_utils.block_entities.block_entity.BlockEntityHealer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockHealer extends Block implements EntityBlock
{
    public static DirectionProperty block_facing = HorizontalDirectionalBlock.FACING;

    public BlockHealer(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(block_facing, Direction.NORTH));
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos)
    {
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof BlockEntityHealer healer)
            return healer;

        return null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
//        if (level.isClientSide())
//            return InteractionResult.FAIL;
//
//        final float health = player.getHealth();
//        final float max_health = player.getMaxHealth();
//
//        if (player instanceof ServerPlayer server_player)
//            server_player.openMenu(state.getMenuProvider(level, pos));
//
//        BlockState rotated_state = state.setValue(block_facing, state.getValue(block_facing).getClockWise());
//        level.setBlock(pos, rotated_state, 3);
//        if (health <= max_health)
//        {
//            float half_hearts = 1;
//
//            player.heal(half_hearts);
//
//            return InteractionResult.SUCCESS;
//        }
//
//        return super.use(state, level, pos, player, hand, hit);

        if (level.isClientSide())
            return InteractionResult.SUCCESS; // Return SUCCESS instead of FAIL

        if (player instanceof ServerPlayer serverPlayer)
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof BlockEntityHealer healer)
                serverPlayer.openMenu(healer);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(block_facing);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState().setValue(block_facing, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new BlockEntityHealer(pos, state);
    }
}
