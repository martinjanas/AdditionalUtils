package additional_utils.items;

import additional_utils.AdditionalUtils;
import additional_utils.blocks.BlockRegistry;
import additional_utils.items.item.ItemSolidifiedXP;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry
{
    public static final DeferredRegister.Items mod_items = DeferredRegister.createItems(AdditionalUtils.MOD_ID);

    public static DeferredItem<Item> solidified_xp;

    //BlockItems down below:

    public static DeferredItem<BlockItem> healer_block_item;
    public static DeferredItem<BlockItem> block_stack_counter_item;

    void register_block_items()
    {
        healer_block_item = mod_items.registerSimpleBlockItem("healer", BlockRegistry.healer_block);
        block_stack_counter_item = mod_items.registerSimpleBlockItem("stack_counter", BlockRegistry.stack_counter_block);
    }

    public void register()
    {
        solidified_xp = mod_items.register("solidified_xp", ItemSolidifiedXP::new);

        register_block_items();
    }
}
