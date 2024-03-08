package additional_utils.registry;

import additional_utils.registry.impl.ModRegistry;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;

public class BlockItemRegistry implements ModRegistry
{
    public static DeferredItem<BlockItem> bi_healer;
    public static DeferredItem<BlockItem> bi_stack_counter;

    public void register()
    {
        final var mod_items = ItemRegistry.mod_items;

        bi_healer = mod_items.registerSimpleBlockItem("bi_healer", BlockRegistry.healer);
        bi_stack_counter = mod_items.registerSimpleBlockItem("bi_stack_counter", BlockRegistry.stack_counter);
    }

    @Override
    public void register_to_bus(IEventBus bus)
    {
        //No need to register anything, we are using ItemRegistry.mod_items anyway.
    }
}
