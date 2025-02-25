package additional_utils.block_entities.block_entity;

import additional_utils.menus.menu.MyMenu;
import additional_utils.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockEntityHealer extends BlockEntity implements MenuProvider
{
    private final SimpleContainer inventory = new SimpleContainer(9);

    public BlockEntityHealer(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityRegistry.healer.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        ListTag inventoryList = new ListTag();

        // Loop through each slot in the NonNullList
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i); // Use items instead of inv
            if (!stack.isEmpty()) // Save only non-empty items
            {
                CompoundTag itemTag = new CompoundTag();
                stack.save(itemTag); // Save the item to a CompoundTag
                inventoryList.add(itemTag); // Add to the inventory list
            }
        }

        // Save the inventory list in the tag
        tag.put("inv", inventoryList); // Save as "inv" or use a custom name

        setChanged(); // Mark the block entity as changed
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        if (tag.contains("inv", Tag.TAG_LIST)) // Check if there is an "inv" tag with item data
        {
            ListTag inventoryList = tag.getList("inv", Tag.TAG_COMPOUND);

            // Loop through the loaded item list and restore them to the NonNullList
            for (int i = 0; i < Math.min(inventoryList.size(), inventory.getContainerSize()); i++) // Prevent overflow
            {
                CompoundTag itemTag = inventoryList.getCompound(i);
                ItemStack stack = ItemStack.of(itemTag); // Load the item from the tag
                inventory.setItem(i, stack); // Set the item in the NonNullList (instead of inv)
            }
        }

        setChanged(); // Mark the block entity as changed
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Healer");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player)
    {
        return new MyMenu(id, player_inventory, inventory);
    }
}
