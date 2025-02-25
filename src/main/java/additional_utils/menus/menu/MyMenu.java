package additional_utils.menus.menu;

import additional_utils.registries.BlockRegistry;
import additional_utils.registries.MenuRegistry;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MyMenu extends AbstractContainerMenu
{
    private final SimpleContainer block_inventory;
    private final int inventory_size;

    public MyMenu(int containerId, Inventory playerInventory, SimpleContainer block_inventory)
    {
        super(MenuRegistry.my_menu.get(), containerId);

        this.block_inventory = block_inventory;
        this.inventory_size = block_inventory.getContainerSize();

        // Add custom slots for the temporary inventory
        int startX = 8;
        int startY = 18;
        for (int i = 0; i < inventory_size; i++)
        {
            this.addSlot(new Slot(block_inventory, i, startX + (i * 18), startY));
        }

        // Add player inventory slots
        addPlayerInventory(playerInventory);
    }

    private void addPlayerInventory(Inventory playerInventory)
    {
        int startX = 8; // Starting x position for the inventory
        int startY = 84; // Starting y position for the inventory

        // Main inventory slots (3 rows of 9)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, startX + col * 18, startY + row * 18));
            }
        }

        // Hotbar slots (1 row of 9)
        startY = 142;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, startX + col * 18, startY));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return stillValid(ContainerLevelAccess.NULL, player, BlockRegistry.healer.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            ItemStack copiedStack = originalStack.copy();

            if (index < inventory_size) {
                // Move from block inventory to player inventory
                if (!this.moveItemStackTo(originalStack, inventory_size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Move from player inventory to block inventory
                if (!this.moveItemStackTo(originalStack, 0, inventory_size, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            return copiedStack;
        }

        return ItemStack.EMPTY;
    }
}