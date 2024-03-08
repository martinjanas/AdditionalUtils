package additional_utils.items;

import additional_utils.blocks.BlockRegistry;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;

public class BlockItemRegistry
{
    public static DeferredItem<BlockItem> healer_block_item;
    public static DeferredItem<BlockItem> block_stack_counter_item;

    public void register()
    {
        final var mod_items = ItemRegistry.mod_items;

        healer_block_item = mod_items.registerSimpleBlockItem("healer", BlockRegistry.healer_block);
        block_stack_counter_item = mod_items.registerSimpleBlockItem("stack_counter", BlockRegistry.stack_counter_block);
    }
}
