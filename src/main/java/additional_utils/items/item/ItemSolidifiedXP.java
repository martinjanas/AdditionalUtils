package additional_utils.items.item;

import additional_utils.registries.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class ItemSolidifiedXP extends Item
{
    public ItemSolidifiedXP()
    {
        super(new Properties().food(new FoodProperties.Builder().alwaysEat().build()));
    }

    public static void OnEntityDropEvent(LivingDropsEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (entity == null)
            return;

        Level level = entity.getCommandSenderWorld();

        if (level.isClientSide())
            return;

        if (!(event.getEntity() instanceof  Mob mob))
            return;

        int looting_level = event.getLootingLevel();

        int amount = level.getRandom().nextIntBetweenInclusive(0, looting_level > 0 ? 5 : 3);

        Vec3 pos = mob.position();

        ItemStack stack = new ItemStack(ItemRegistry.solidified_xp.asItem(), amount);

        event.getDrops().add(new ItemEntity(level, pos.x, pos.y, pos.z, stack));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living_entity)
    {
        if (level.isClientSide())
            return stack;

        if (!(living_entity instanceof Player player))
            return stack;

        if (player.isCreative())
            return stack;

        int base_xp = 8;

        final boolean is_shifting = player.isShiftKeyDown();

        int xp_to_give = is_shifting ? stack.getCount() * base_xp : base_xp;

        player.giveExperiencePoints(xp_to_give);
        stack.shrink(is_shifting ? stack.getCount() : 1);

        return super.finishUsingItem(stack, level, living_entity);
    }
}
