package additional_utils.api.event;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.items.item.ItemSolidifiedXP;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(modid = AdditionalUtils.MOD_ID) //You can add this before the EventManager class and you don't have to call NeoForge.EVENT_BUS.register(ModEventManager.class);
public class EventManager
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
        BlockEntityStackCounter.OnPlayerInteract(event);
    }
}
