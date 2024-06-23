package additional_utils.api.event;

import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.items.item.ItemSolidifiedXP;
import additional_utils.registry.MenuRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

//@Mod.EventBusSubscriber() - You can add this before the ModEventManager class and you dont have to call NeoForge.EVENT_BUS.register(ModEventManager.class);
public class ModEventManager
{
    @SuppressWarnings("unused")
    @SubscribeEvent
    private static void OnEntityDropEvent(LivingDropsEvent event)
    {
        ItemSolidifiedXP.OnEntityDropEvent(event);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void OnPlayerInteractEvent(PlayerInteractEvent.RightClickBlock event)
    {
        //Figure out if you can use PlayerInteractEvent for more than RightClickBlock event
        BlockEntityStackCounter.OnPlayerInteract(event);
    }

    /*@SuppressWarnings("unused")
    @SubscribeEvent
    public static void OnRegisterScreens(RegisterMenuScreensEvent event) //Broken
    {
        //event.register(MenuRegistry.my_menu.get(), additional_utils.menus.menu.MyScreen::new);
    }*/
}
