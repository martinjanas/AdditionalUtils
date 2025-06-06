package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTabRegistry implements ModRegistry
{
    public static final DeferredRegister<CreativeModeTab> tabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdditionalUtils.MOD_ID);

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> creative_tab;

    @Override
    public void register(IEventBus bus)
    {
        var builder = CreativeModeTab.builder();
        builder.title(Component.translatable("itemGroup.au_creative_tab"));
        builder.withTabsBefore(CreativeModeTabs.COMBAT);
        builder.icon(() -> new ItemStack(ItemRegistry.solidified_xp.get()));
        builder.displayItems((params, output) -> {
            for (DeferredHolder<Item, ? extends Item> item_stack : ItemRegistry.items.getEntries())
            {
                output.accept(item_stack.get());
            }
        });
        CreativeModeTab tab = builder.build();

        creative_tab = tabs.register(AdditionalUtils.MOD_ID.concat("_creative_tab"), () -> tab);

        tabs.register(bus);
    }
}
