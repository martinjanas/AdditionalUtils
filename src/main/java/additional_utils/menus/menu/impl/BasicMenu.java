package additional_utils.menus.menu.impl;

import additional_utils.api.slot.BarrelSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public abstract class BasicMenu extends AbstractContainerMenu
{
    private final int inventorySize;

    public BasicMenu(MenuType<? extends AbstractContainerMenu> menuType, int containerId, Inventory playerInventory, SimpleContainer blockInventory)
    {
        super(menuType, containerId);
        this.inventorySize = blockInventory.getContainerSize();

        // Add block inventory slots
        int startX = 8;
        int startY = 18;
        for (int i = 0; i < inventorySize; i++) {
            this.addSlot(new BarrelSlot(blockInventory, i, startX + (i * 18), startY, 128));
        }

        // Add player inventory slots
        addPlayerInventory(playerInventory);
    }

    // Add player inventory
    private void addPlayerInventory(Inventory playerInventory)
    {
        int startX = 8;
        int startY = 84;

        // Main inventory (3 rows of 9)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, startX + col * 18, startY + row * 18));
            }
        }

        // Hotbar (1 row of 9)
        startY = 142;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, startX + col * 18, startY));
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

            if (index < inventorySize)
            {
                // Move from block inventory to player inventory
                if (!this.moveItemStackTo(originalStack, inventorySize, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else {
                // Move from player inventory to block inventory
                if (!this.moveItemStackTo(originalStack, 0, inventorySize, false))
                    return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty())
                slot.set(ItemStack.EMPTY);
            else slot.setChanged(); // Notify slot that the item changed

            return copiedStack;
        }

        return ItemStack.EMPTY;
    }
}
