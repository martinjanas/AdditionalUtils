package additional_utils.blocks.block;

import additional_utils.registries.BlockEntityRegistry;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.registries.BlockItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;

import javax.annotation.Nullable;
import java.util.*;

public class BlockStackCounter extends Block implements EntityBlock
{
    public BlockEntityStackCounter counter;

    @Nullable
    public UUID block_owner_uuid = null;

    public BlockStackCounter()
    {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return counter = BlockEntityRegistry.be_stack_counter.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return type == BlockEntityRegistry.be_stack_counter.get() ? BlockEntityStackCounter::tick : null;

        //return EntityBlock.super.getTicker(level, state, type);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack)
    {
        if (!pLevel.isClientSide() && pPlacer != null)
        {
            block_owner_uuid = pPlacer.getUUID();
        }

        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams)
    {
        List<ItemStack> item_list = super.getDrops(pState, pParams);

        item_list.add(new ItemStack(BlockItemRegistry.bi_stack_counter.get()));

        return item_list;
    }
}
