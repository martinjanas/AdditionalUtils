package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.menus.menu.MyMenu;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuRegistry implements ModRegistry
{
    public static final DeferredRegister<MenuType<?>> menus = DeferredRegister.create(BuiltInRegistries.MENU, AdditionalUtils.MOD_ID);

    public static DeferredHolder<MenuType<?>, MenuType<MyMenu>> my_menu;

    @Override
    public void register(IEventBus bus)
    {
        //my_menu = menus.register("my_menu", () -> IMenuTypeExtension.create(MyMenu::new));
        my_menu = menus.register("my_menu", () -> new MenuType<>(MyMenu::new, FeatureFlags.DEFAULT_FLAGS));

        menus.register(bus);
    }
}
