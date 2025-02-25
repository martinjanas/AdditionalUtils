package additional_utils.menus.menu.barrel;

import additional_utils.menus.menu.impl.BasicScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class BarrelScreen extends BasicScreen
{
    public BarrelScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
    }
}
