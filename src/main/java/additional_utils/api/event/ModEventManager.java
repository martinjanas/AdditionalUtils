package additional_utils.api.event;

import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.items.item.ItemSolidifiedXP;
import net.neoforged.bus.api.SubscribeEvent;
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
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event)
    {
        BlockEntityStackCounter.OnPlayerInteract(event);
    }
}
