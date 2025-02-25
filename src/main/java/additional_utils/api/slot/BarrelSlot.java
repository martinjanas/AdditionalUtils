package additional_utils.api.slot;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BarrelSlot extends Slot
{
    private final int maxStackSize;

    public BarrelSlot(SimpleContainer inventory, int index, int x, int y, int maxStackSize)
    {
        super(inventory, index, x, y);
        this.maxStackSize = maxStackSize;
    }

    @Override
    public int getMaxStackSize()
    {
        return maxStackSize;
    }

    @Override
    public int getMaxStackSize(ItemStack pStack)
    {
        return maxStackSize;
    }
}
