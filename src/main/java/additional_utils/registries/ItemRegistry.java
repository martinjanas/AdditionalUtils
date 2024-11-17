package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.items.item.ItemSolidifiedXP;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry implements ModRegistry
{
    public static final DeferredRegister.Items items = DeferredRegister.createItems(AdditionalUtils.MOD_ID);

    public static DeferredItem<Item> solidified_xp;

    @Override
    public void register(IEventBus bus)
    {
        solidified_xp = items.register("solidified_xp", ItemSolidifiedXP::new);

        items.register(bus);
    }
}
