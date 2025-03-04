package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockItemRegistry implements ModRegistry
{
    public static DeferredItem<BlockItem> healer;
    public static DeferredItem<BlockItem> crafter;
    public static DeferredItem<BlockItem> barrel;
    public static DeferredItem<BlockItem> generator;

    @Override
    public void register(IEventBus bus)
    {
        var items = ItemRegistry.items;

        healer = items.registerSimpleBlockItem("healer", BlockRegistry.healer);
        crafter = items.registerSimpleBlockItem("crafter", BlockRegistry.crafter);
        barrel = items.registerSimpleBlockItem("barrel", BlockRegistry.barrel);
        generator = items.registerSimpleBlockItem("generator", BlockRegistry.generator);
    }
}
