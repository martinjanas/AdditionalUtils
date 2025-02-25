package additional_utils.menus.menu.crafter;

import additional_utils.menus.menu.impl.BasicMenu;
import additional_utils.registries.BlockRegistry;
import additional_utils.registries.MenuRegistry;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class CrafterMenu extends BasicMenu
{
    public CrafterMenu(int containerId, Inventory playerInventory, SimpleContainer blockInventory)
    {
        super(MenuRegistry.crafter_menu.get(), containerId, playerInventory, blockInventory);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(ContainerLevelAccess.NULL, player, BlockRegistry.crafter.get());
    }
}
