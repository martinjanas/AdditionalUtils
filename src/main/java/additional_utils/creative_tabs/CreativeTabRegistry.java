package additional_utils.creative_tabs;

import additional_utils.AdditionalUtils;
import additional_utils.items.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabRegistry
{
    public static final DeferredRegister<CreativeModeTab> mod_tabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdditionalUtils.MOD_ID);

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> creative_tab;

    public void register()
    {
        var builder = CreativeModeTab.builder();
        //builder.title(Component.translatable("itemGroup.creative_tab"));
        builder.title(Component.literal("Additional Utils"));
        builder.withTabsBefore(CreativeModeTabs.COMBAT);
        builder.icon(() -> new ItemStack(Items.DIAMOND));
        builder.displayItems((params, output) -> {
            for (DeferredHolder<Item, ? extends Item> item_stack : ItemRegistry.mod_items.getEntries())
            {
                output.accept(item_stack.get());
            }
        });
        CreativeModeTab tab = builder.build();

        creative_tab = mod_tabs.register(AdditionalUtils.MOD_ID.concat("_creative_tab"), () -> tab);
    }
}
