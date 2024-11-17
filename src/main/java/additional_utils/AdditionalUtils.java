package additional_utils;

import additional_utils.api.event.EventManager;
import additional_utils.registries.*;
import additional_utils.registries.impl.ModRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
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
        bus.addListener(this::OnRegisterScreens);

        for (ModRegistry registry : mod_registries)
             registry.register(bus);

        //NeoForge.EVENT_BUS.register(EventManager.class);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void OnRegisterScreens(RegisterMenuScreensEvent event)
    {
        event.register(MenuRegistry.my_menu.get(), additional_utils.menus.menu.MyScreen::new);
    }

    private void client_setup(final FMLClientSetupEvent event)
    {

    }

    private void common_setup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
}
