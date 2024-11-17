package additional_utils.blocks.block;

import additional_utils.menus.menu.MyMenu;
import net.minecraft.client.gui.font.providers.UnihexProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.IPlayerExtension;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BlockHealer extends Block
{
    public static DirectionProperty block_facing = HorizontalDirectionalBlock.FACING;

    public BlockHealer(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(block_facing, Direction.NORTH));
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos)
    {
        return new SimpleMenuProvider((id, inv, pl) -> new MyMenu(id, inv), Component.literal("MyMenu"));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (level.isClientSide())
            return InteractionResult.FAIL;

        final float health = player.getHealth();
        final float max_health = player.getMaxHealth();

        if (player instanceof ServerPlayer server_player)
            server_player.openMenu(state.getMenuProvider(level, pos));

        BlockState rotated_state = state.setValue(block_facing, state.getValue(block_facing).getClockWise());
        level.setBlock(pos, rotated_state, 3);
        if (health <= max_health)
        {
            float half_hearts = 1;

            player.heal(half_hearts);

            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
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
}
