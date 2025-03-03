package additional_utils.block_entities.block_entity;

import additional_utils.api.inventory_containers.BarrelContainer;
import additional_utils.menus.menu.barrel.BarrelMenu;
import additional_utils.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockEntityBarrel extends BlockEntity implements MenuProvider
{
    public static int MAX_DEFAULT_STACK_SIZE = 512;
    public BarrelContainer inventory = new BarrelContainer(MAX_DEFAULT_STACK_SIZE);

    public BlockEntityBarrel(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityRegistry.barrel.get(), pPos, pBlockState);
    }

    public void OnPlayerRightClick(Level level, Player player, InteractionHand hand, BlockState state, BlockPos pos)
    {
        if (level.isClientSide())
            return;

        // Get the player and the item in their hand
        ItemStack heldItem = player.getItemInHand(hand);

        // Prevent action if the player is holding nothing or pressing shift
        if (heldItem.isEmpty() || player.isShiftKeyDown())
            return;

        // Get the slot where the item is going to be added
        ItemStack slotStack = inventory.getItem(0);
        int maxStack = inventory.getMaxStackSize();

        // If the slot is empty, add the held item to the slot
        if (slotStack.isEmpty())
        {
            ItemStack newStack = heldItem.copy();
            newStack.setCount(Math.min(heldItem.getCount(), maxStack));
            inventory.setItem(0, newStack);
            heldItem.shrink(newStack.getCount());
        }
        else if (ItemStack.isSameItemSameTags(slotStack, heldItem) && slotStack.getCount() < maxStack)
        {
            // If the item is the same and we can stack them, add more to the existing stack
            int transferable = Math.min(heldItem.getCount(), maxStack - slotStack.getCount());
            slotStack.grow(transferable);
            heldItem.shrink(transferable);
        }
        else
        {
            // Inventory is full or incompatible item, display message
            if (!level.isClientSide())
                player.displayClientMessage(Component.literal("Inventory is full!"), true);
        }

        // Mark the block entity as changed
        inventory.setChanged();
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        // Create a new ListTag to store the item stack (even though there's only one item)
        ListTag inventoryList = new ListTag();

        // Get the item stack from the barrel inventory
        ItemStack stack = inventory.getItem(0);
        if (!stack.isEmpty())
        {
            // Create a new tag to save the item stack data
            CompoundTag itemTag = new CompoundTag();
            stack.save(itemTag);

            // Store the stack's count (this step might be redundant since the stack already contains the count)
            itemTag.putInt("Count", stack.getCount());

            // Add the itemTag to the inventory list (even though it only contains one item)
            inventoryList.add(itemTag);
        }

        // Put the inventory list into the tag
        tag.put("barrel_inventory", inventoryList);

        setChanged();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        // Check if the "barrel_inventory" tag exists and is a list of compounds
        if (tag.contains("barrel_inventory", Tag.TAG_LIST))
        {
            ListTag inventoryList = tag.getList("barrel_inventory", Tag.TAG_COMPOUND);

            // Since we only have one item, we can directly access the first (and only) element
            if (!inventoryList.isEmpty())
            {
                CompoundTag itemTag = inventoryList.getCompound(0);
                ItemStack stack = ItemStack.of(itemTag);

                // Restore the count of the item (in case it was modified outside of this method)
                stack.setCount(itemTag.getInt("Count"));

                // Set the item stack in the inventory (slot 0)
                inventory.setItem(0, stack);
            }
        }

        setChanged();
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Barrel");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player)
    {
        return new BarrelMenu(id, player_inventory, inventory);
    }

    public ItemStack getDisplayedItem()
    {
        return inventory.getItem(0);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        var tag = super.getUpdateTag();
        saveAdditional(tag);

        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
        super.handleUpdateTag(tag);
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        requestModelDataUpdate(); // Tell client to refresh rendering
    }

}
