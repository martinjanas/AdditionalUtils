package additional_utils.menus.menu.barrel;

import additional_utils.api.inventory_containers.BarrelContainer;
import additional_utils.api.slot.BarrelSlot;
import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.registries.BlockRegistry;
import additional_utils.registries.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;


public class BarrelMenu extends AbstractContainerMenu
{
    public int inventory_size;
    Inventory player_inventory;

    //Client constructor
    public BarrelMenu(int container_id, Inventory player_inventory, FriendlyByteBuf buf)
    {
        this(container_id, player_inventory, new BarrelContainer(BlockEntityBarrel.MAX_DEFAULT_STACK_SIZE));
    }

    //Server constructor
    public BarrelMenu(int container_id, Inventory player_inventory, BarrelContainer barrel_inventory)
    {
        super(MenuRegistry.barrel_menu.get(), container_id);
        this.inventory_size = barrel_inventory.getMaxStackSize();
        this.player_inventory = player_inventory;

        addSlot(new BarrelSlot(barrel_inventory, 0, 8, 18, inventory_size));

        //Adds the player inventory and hotbar
        int startX = 8;
        int startY = 84;

        // Main inventory (3 rows of 9)
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                this.addSlot(new Slot(player_inventory, col + row * 9 + 9, startX + col * 18, startY + row * 18));
            }
        }

        // Hotbar (1 row of 9)
        startY = 142;
        for (int col = 0; col < 9; col++)
        {
            this.addSlot(new Slot(player_inventory, col, startX + col * 18, startY));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem())
        {
            ItemStack originalStack = slot.getItem();
            ItemStack copiedStack = originalStack.copy();

            if (index < inventory_size)
            {
                // Move from barrel inventory to player inventory
                if (!this.moveItemStackTo(originalStack, inventory_size, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else {
                // Move from player inventory to barrel inventory
                if (!this.moveItemStackTo(originalStack, 0, inventory_size, false))
                    return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty())
                slot.set(ItemStack.EMPTY);
            else slot.setChanged(); // Mark the slot as changed

            return copiedStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(ContainerLevelAccess.NULL, player, BlockRegistry.barrel.get());
    }
}
