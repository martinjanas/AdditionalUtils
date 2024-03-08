package additional_utils.registry;

import additional_utils.AdditionalUtils;
import additional_utils.items.item.ItemSolidifiedXP;
import additional_utils.registry.impl.ModRegistry;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry implements ModRegistry
{
    public static final DeferredRegister.Items mod_items = DeferredRegister.createItems(AdditionalUtils.MOD_ID);

    public static DeferredItem<Item> solidified_xp;

    public void register()
    {
        solidified_xp = mod_items.register("solidified_xp", ItemSolidifiedXP::new);
    }

    @Override
    public void register_to_bus(IEventBus bus)
    {
        mod_items.register(bus);
    }
}
