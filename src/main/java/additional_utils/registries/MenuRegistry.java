package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.api.inventory_containers.CustomSimpleContainer;
import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.menus.menu.barrel.BarrelMenu;
import additional_utils.menus.menu.crafter.CrafterMenu;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuRegistry implements ModRegistry
{
    public static final DeferredRegister<MenuType<?>> menus = DeferredRegister.create(BuiltInRegistries.MENU, AdditionalUtils.MOD_ID);

    public static DeferredHolder<MenuType<?>, MenuType<CrafterMenu>> crafter_menu;
    public static DeferredHolder<MenuType<?>, MenuType<BarrelMenu>> barrel_menu;

    @Override
    public void register(IEventBus bus)
    {
        crafter_menu = menus.register("crafter_menu", () -> new MenuType<>((id, playerInventory) -> new CrafterMenu(id, playerInventory, new SimpleContainer(3)), FeatureFlags.DEFAULT_FLAGS));
        barrel_menu = menus.register("barrel_menu", () -> new MenuType<>((id, playerInventory) -> new BarrelMenu(id, playerInventory, new CustomSimpleContainer(1, 128)), FeatureFlags.DEFAULT_FLAGS));

        menus.register(bus);
    }
}
