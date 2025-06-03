package additional_utils.api.event;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.items.item.ItemSolidifiedXP;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//You can add this before the EventManager class and you don't have to call NeoForge.EVENT_BUS.register(ModEventManager.class);
@Mod.EventBusSubscriber(modid = AdditionalUtils.MOD_ID)
public class EventManager
{
    private static final List<Creeper> fakeCreepers = new ArrayList<>();
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

    private static boolean isPlayerLookingAt(Player player, Entity entity)
    {
        double xDiff = entity.getX() - player.getX();
        double zDiff = entity.getZ() - player.getZ();
        double angle = Math.toDegrees(Math.atan2(zDiff, xDiff)) - 90;
        double playerYaw = player.getYRot() % 360;

        // Normalize angles to [0, 360]
        if (angle < 0) angle += 360;
        if (playerYaw < 0) playerYaw += 360;

        // Check if player's view is within 60-degree cone
        return Math.abs(playerYaw - angle) < 30;
    }

    private static double randomOffset(Random random)
    {
        return (random.nextDouble() - 0.5) * 6.0; // Random offset around real Creeper
    }

    @SubscribeEvent
    public static void onVillagerAIUpdate(LivingEvent.LivingTickEvent event)
    {
        Random random = new Random();
        if (event.getEntity() instanceof Villager villager && !villager.level().isClientSide) {
            Player player = villager.level().getNearestPlayer(villager, 50);
            if (player != null) {
                // Check if the player is looking at the villager
                boolean isLooking = isPlayerLookingAt(player, villager);

                if (!isLooking) {
                    // Stop AI before moving
                    villager.getNavigation().stop();
                    villager.getNavigation().moveTo(player, 1.0);
                } else {
                    // Freeze them completely when looked at
                    villager.getNavigation().stop();
                    villager.setDeltaMovement(0, 0, 0);
                }

                // Explode the villager when they are close to the player
                double distance = villager.distanceToSqr(player);
                double explosionRange = 2.0; // Adjust this value for desired explosion range

                if (distance <= explosionRange)
                {
                    // Create an explosion at the villager's position
                    villager.level().explode(villager, villager.getX(), villager.getY(), villager.getZ(), 1.5f, Level.ExplosionInteraction.MOB);
                    villager.remove(Entity.RemovalReason.DISCARDED); // Remove the villager after the explosion
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
//        Random random = new Random();
//        if (!event.player.level().isClientSide && event.player instanceof ServerPlayer serverPlayer) {
//            if (random.nextInt(1000) == 0) { // Occasionally send a creepy message
//                serverPlayer.sendSystemMessage(Component.literal("You feel like someone is watching you..."));
//            }
//        }
    }
}
