package additional_utils.registry;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.menus.menu.MyMenu;
import additional_utils.registry.impl.ModRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuRegistry implements ModRegistry
{
    public static final DeferredRegister<MenuType<?>> mod_menus = DeferredRegister.create(BuiltInRegistries.MENU, AdditionalUtils.MOD_ID);

    public static DeferredHolder<MenuType<?>, MenuType<MyMenu>> my_menu;

    @Override
    public void register()
    {
        //my_menu = mod_menus.register("my_menu", () -> IMenuTypeExtension.create(MyMenu::new));
        my_menu = mod_menus.register("my_menu", () -> new MenuType<>(MyMenu::new, FeatureFlags.DEFAULT_FLAGS));
    }

    @Override
    public void register_to_bus(IEventBus bus)
    {
        mod_menus.register(bus);
    }
}
