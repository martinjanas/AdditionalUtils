package additional_utils.api.inventory_containers;

import net.minecraft.world.SimpleContainer;

public class CustomSimpleContainer extends SimpleContainer
{
    private final int maxStackSize;

    public CustomSimpleContainer(int size, int maxStackSize)
    {
        super(size);
        this.maxStackSize = maxStackSize;
    }

    @Override
    public int getMaxStackSize()
    {
        return maxStackSize;
    }
}
