package additional_utils.blocks.block;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.block_entities.block_entity.BlockEntityCrafter;
import additional_utils.menus.menu.barrel.BarrelMenu;
import additional_utils.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockBarrel extends Block implements EntityBlock
{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; // Only 4 directions (N, S, E, W)
    public BlockBarrel(Properties properties)
    {
        super(properties);
       registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING); // Register the FACING property
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return BlockEntityRegistry.barrel.get().create(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!level.isClientSide())
        {
            if (!state.is(newState.getBlock()))
            {
                BlockEntity block_entity = level.getBlockEntity(pos);

                if (block_entity != null)
                {
                    ItemStack stack = new ItemStack(this);

                    CompoundTag blockTag = block_entity.saveWithFullMetadata();
                    stack.getOrCreateTag().put("BlockEntityTag", blockTag);

                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                }

                super.onRemove(state, level, pos, newState, isMoving);
            }
        }
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

        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof BlockEntityBarrel barrel)
            barrel.OnPlayerRightClick(level, player, hand, block_state, pos);

        return InteractionResult.CONSUME;
    }
}
