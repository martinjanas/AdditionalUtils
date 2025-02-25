package additional_utils.block_entities.block_entity;

import additional_utils.registries.BlockEntityRegistry;
import additional_utils.blocks.block.BlockStackCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.UUID;

public class BlockEntityStackCounter extends BlockEntity
{
    private SimpleContainer inv;

    //This currently acts like a barrel kind of blockentity - used to insert and retrieve items in it
    public BlockEntityStackCounter(BlockPos pos, BlockState state)
    {
        super(BlockEntityRegistry.stack_counter.get(), pos, state);

        inv = new SimpleContainer(3);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T e)
    {
        return;
    }

    public void insert_item(Player player, InteractionHand hand)
    {
        Level level = player.level();
        if (level.isClientSide() || hand != InteractionHand.MAIN_HAND)
            return;

        ItemStack hand_stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack stack_copy = hand_stack.copy();

        final boolean is_shifting = player.isShiftKeyDown();
        if (!is_shifting)
            stack_copy.setCount(1);

        ItemStack remainder = inv.addItem(stack_copy);
        if (remainder.isEmpty())
        {
            hand_stack.shrink(is_shifting ? stack_copy.getCount() : 1);
        }
    }

    public void retrieve_item(Player player, InteractionHand hand)
    {
        Level level = player.level();
        if (level.isClientSide() || hand != InteractionHand.MAIN_HAND)
            return;

        final boolean is_shifting = player.isShiftKeyDown();
        for (int i = 0; i < inv.getContainerSize(); i++)
        {
            ItemStack stack = inv.getItem(i);

            if (!stack.isEmpty() && stack.getCount() > 0)
            {
                player.getInventory().add(new ItemStack(stack.getItem(), is_shifting ? stack.getCount() : 1));
                stack.shrink(is_shifting ? stack.getCount() : 1);

                setChanged();
                break;
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        ListTag inventoryList = new ListTag();
        for (int i = 0; i < inv.getContainerSize(); i++)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty())
            {
                CompoundTag itemTag = new CompoundTag();
                stack.save(itemTag);

                inventoryList.add(itemTag);
            }
        }

        tag.put("inv", inventoryList);

        setChanged();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        if (tag.contains("inv", Tag.TAG_LIST))
        {
            ListTag inventoryList = tag.getList("inv", Tag.TAG_COMPOUND);
            for (int i = 0; i < inventoryList.size(); i++)
            {
                CompoundTag itemTag = inventoryList.getCompound(i);
                ItemStack stack =  ItemStack.of(itemTag);

                inv.setItem(i, stack);
            }
        }

        setChanged();
    }
}
