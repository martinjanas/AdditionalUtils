package additional_utils.items;

import additional_utils.AdditionalUtils;
import additional_utils.items.item.ItemSolidifiedXP;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry
{
    public static final DeferredRegister.Items mod_items = DeferredRegister.createItems(AdditionalUtils.MOD_ID);

    public static DeferredItem<Item> solidified_xp;

    public void register()
    {
        solidified_xp = mod_items.register("solidified_xp", ItemSolidifiedXP::new);
    }
}
