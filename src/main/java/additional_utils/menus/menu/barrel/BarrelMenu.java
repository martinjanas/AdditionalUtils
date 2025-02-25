package additional_utils.menus.menu.barrel;

import additional_utils.menus.menu.impl.BasicMenu;
import additional_utils.registries.BlockRegistry;
import additional_utils.registries.MenuRegistry;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BarrelMenu extends BasicMenu
{
    public BarrelMenu(int containerId, Inventory playerInventory, SimpleContainer blockInventory)
    {
        super(MenuRegistry.barrel_menu.get(), containerId, playerInventory, blockInventory);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(ContainerLevelAccess.NULL, player, BlockRegistry.barrel.get());
    }
}
