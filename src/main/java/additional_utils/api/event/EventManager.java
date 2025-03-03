package additional_utils.api.event;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.items.item.ItemSolidifiedXP;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

//You can add this before the EventManager class and you don't have to call NeoForge.EVENT_BUS.register(ModEventManager.class);
@Mod.EventBusSubscriber(modid = AdditionalUtils.MOD_ID)
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
    public static void OnPlayerRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        var level = event.getLevel();
        if (level.isClientSide())
            return;

//        BlockEntity block_entity = level.getBlockEntity(event.getPos());
//        if (block_entity instanceof BlockEntityBarrel barrel)
//            barrel.OnPlayerRightClick(event);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void OnPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event)
    {
        var level = event.getLevel();
        if (level.isClientSide())
            return;

        BlockEntity block_entity = level.getBlockEntity(event.getPos());
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void OnBlockStartBreak(BlockEvent.BreakEvent event)
    {
//        if (event.getLevel().getBlockEntity(event.getPos()) instanceof BlockEntityStackCounter)
//            event.setCanceled(true);
    }
}
