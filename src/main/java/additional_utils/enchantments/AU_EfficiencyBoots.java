package additional_utils.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AU_EfficiencyBoots  extends Enchantment
{
    public AU_EfficiencyBoots()
    {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] { EquipmentSlot.FEET });
    }

    @Override
    public int getMaxLevel()
    {
        //702980517
        return 100000;
    }
}
