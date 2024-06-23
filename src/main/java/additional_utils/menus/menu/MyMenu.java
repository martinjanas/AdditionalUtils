package additional_utils.menus.menu;


import additional_utils.AdditionalUtils;
import additional_utils.registry.BlockRegistry;
import additional_utils.registry.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MyMenu extends AbstractContainerMenu
{
    //private final ContainerLevelAccess containerAccess;

    public MyMenu(int containerId, Inventory playerInventory) {
        super(MenuRegistry.my_menu.get(), containerId);
        System.out.println("Hello from MyMenu()");;
        //containerAccess = ContainerLevelAccess.create(playerInventory.player.level(), pos);

        /*IItemHandler itemHandler = playerInventory.player.level().getBlockEntity(pos);

        IItemHandler itemHandler = playerInventory.player.level.getBlockEntity(pos)
                .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(IllegalStateException::new);

        // Add custom slots here, for example:
        addSlot(new SlotItemHandler(itemHandler, 0, 80, 35));

        // Add player inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }*/
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.NULL, player, BlockRegistry.healer.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 1) {
                if (!this.moveItemStackTo(itemstack1, 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
}