package additional_utils.items.item;

import additional_utils.items.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class ItemSolidifiedXP extends Item
{
    public ItemSolidifiedXP()
    {
        super(new Properties().food(new FoodProperties.Builder().alwaysEat().build()));
    }

    private static void OnEntityDropEvent(LivingDropsEvent event)
    {
        Level level = event.getEntity().getCommandSenderWorld();

        if (level.isClientSide())
            return;

        if (event.getEntity() instanceof Mob mob)
        {
            int looting_level = event.getLootingLevel();

            int amount = level.getRandom().nextIntBetweenInclusive(0, looting_level > 0 ? 5 : 3);

            Vec3 pos = mob.position();

            ItemStack stack = new ItemStack(ItemRegistry.solidified_xp.asItem(), amount);

            event.getDrops().add(new ItemEntity(level, pos.x, pos.y, pos.z, stack));
        }
    }

    @SubscribeEvent
    public static void onEntityDropEvent(LivingDropsEvent event)
    {
        OnEntityDropEvent(event);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity)
    {
        if (!pLevel.isClientSide())
        {
            int base_xp = 8;

            if (pLivingEntity instanceof Player player)
            {
                if (!player.isCreative())
                {
                    if (player.isShiftKeyDown())
                    {
                        int xp_to_give = pStack.getCount() * base_xp;

                        player.giveExperiencePoints(xp_to_give);
                        pStack.shrink(pStack.getCount());
                    }
                    else
                    {
                        player.giveExperiencePoints(base_xp);
                    }
                }
            }
        }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
