package additional_utils.api.inventory_containers;

import net.minecraft.world.SimpleContainer;

public class BarrelContainer extends SimpleContainer
{
    private final int max_stack_size;
    public BarrelContainer(int max_stack_size)
    {
        super(1);
        this.max_stack_size = max_stack_size;
    }

    @Override
    public int getMaxStackSize()
    {
        return max_stack_size;
    }
}
