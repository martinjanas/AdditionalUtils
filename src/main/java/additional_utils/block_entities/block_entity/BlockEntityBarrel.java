package additional_utils.block_entities.block_entity;

import additional_utils.api.inventory_containers.CustomSimpleContainer;
import additional_utils.menus.menu.barrel.BarrelMenu;
import additional_utils.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.Nullable;

public class BlockEntityBarrel extends BlockEntity implements MenuProvider
{
    public final CustomSimpleContainer inventory = new CustomSimpleContainer(1, 128);

    public BlockEntityBarrel(BlockPos pPos, BlockState pBlockState)
    {
        super(BlockEntityRegistry.barrel.get(), pPos, pBlockState);
    }

    public void OnPlayerRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack heldItem = player.getItemInHand(event.getHand());

        if (heldItem.isEmpty() || player.isShiftKeyDown())
            return;

        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack slotStack = inventory.getItem(i);

            int maxStack = inventory.getMaxStackSize();
            if (slotStack.isEmpty())
            {
                ItemStack newStack = heldItem.copy();
                newStack.setCount(Math.min(heldItem.getCount(), maxStack));
                inventory.setItem(i, newStack);
                heldItem.shrink(newStack.getCount());

                setChanged();
                event.setCanceled(true);

                return;
            }

            if (ItemStack.isSameItemSameTags(slotStack, heldItem) && slotStack.getCount() < maxStack)
            {
                int transferable = Math.min(heldItem.getCount(), maxStack - slotStack.getCount());
                slotStack.grow(transferable);
                heldItem.shrink(transferable);

                setChanged();
                event.setCanceled(true);

                return;
            }
        }

        if (!level.isClientSide())
            player.displayClientMessage(Component.literal("Inventory is full!"), true);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        ListTag inventoryList = new ListTag();

        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty())
            {
                CompoundTag itemTag = new CompoundTag();
                stack.save(itemTag);
                inventoryList.add(itemTag);
            }
        }

        //The nbt tag name must be unique, else if you use the same name on more than one block entity it will fail to save
        tag.put("barrel_inventory", inventoryList);

        setChanged();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        if (tag.contains("barrel_inventory", Tag.TAG_LIST))
        {
            ListTag inventoryList = tag.getList("barrel_inventory", Tag.TAG_COMPOUND);

            // Loop through the loaded item list and restore them to the NonNullList
            for (int i = 0; i < Math.min(inventoryList.size(), inventory.getContainerSize()); i++)
            {
                CompoundTag itemTag = inventoryList.getCompound(i);
                ItemStack stack = ItemStack.of(itemTag);
                inventory.setItem(i, stack);
            }
        }

        setChanged();
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Item Barrel");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player)
    {
        return new BarrelMenu(id, player_inventory, inventory);
    }
}
