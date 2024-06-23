package additional_utils;

import additional_utils.api.event.ModEventManager;
import additional_utils.menus.menu.MyMenu;
import additional_utils.registry.*;
import additional_utils.registry.impl.ModRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.List;

@Mod(AdditionalUtils.MOD_ID)
public class AdditionalUtils
{
    public static final String MOD_ID = "adutils";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AdditionalUtils(IEventBus bus)
    {
        List<ModRegistry> mod_registries = List.of(new ItemRegistry(), new BlockRegistry(), new BlockItemRegistry(),
                new BlockEntityRegistry(), new CreativeTabRegistry(), new MenuRegistry());

        bus.addListener(this::common_setup);
        bus.addListener(this::client_setup);

        for (ModRegistry registry : mod_registries)
        {
            registry.register();
            registry.register_to_bus(bus);
        }
        //bus.register(ModEventManager.class);
        NeoForge.EVENT_BUS.register(ModEventManager.class);
    }

    private void client_setup(final FMLClientSetupEvent event)
    {
        //event.enqueueWork(() -> MenuScreens.register(MenuRegistry.my_menu.get(),);

        /*event.enqueueWork(() -> {
            MenuScreens.register(MenuRegistry.my_menu.get(), InventoryScreen);
        });*/
    }

    private void common_setup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
}
