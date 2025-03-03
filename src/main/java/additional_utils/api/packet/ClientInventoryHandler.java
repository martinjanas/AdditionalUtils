package additional_utils.api.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ClientInventoryHandler
{
    public static void handleInventoryUpdate(InventoryUpdatePacket packet)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.containerMenu.containerId == packet.getContainerId())
        {
            AbstractContainerMenu menu = mc.player.containerMenu;
            List<ItemStack> items = packet.getItems();

            for (int i = 0; i < items.size(); i++)
            {
                if (i < menu.slots.size())
                {
                    ItemStack stack = items.get(i);
                    if (!stack.isEmpty())
                    {
                        stack.setCount(Math.min(stack.getCount(), 9999)); // Ensure UI displays it properly
                    }

                    menu.slots.get(i).set(stack);
                }
            }
        }
    }
}

