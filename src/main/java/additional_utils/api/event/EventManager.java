package additional_utils.api.event;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityCrafter;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.items.item.ItemSolidifiedXP;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

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
    public static void OnPlayerRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        var level = event.getLevel();
        if (level.isClientSide())
            return;

        BlockEntity blockEntity = level.getBlockEntity(event.getPos());
        if (blockEntity instanceof BlockEntityStackCounter entity)
            entity.insert_item(event.getEntity(), event.getHand());
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void OnPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event)
    {
        var level = event.getLevel();
        if (level.isClientSide())
            return;

        BlockEntity blockEntity = level.getBlockEntity(event.getPos());
        if (blockEntity instanceof BlockEntityStackCounter entity)
            entity.retrieve_item(event.getEntity(), event.getHand());
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void OnBlockStartBreak(BlockEvent.BreakEvent event)
    {
        if (event.getLevel().getBlockEntity(event.getPos()) instanceof BlockEntityStackCounter)
            event.setCanceled(true);
    }
}
