package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.enchantments.AU_EfficiencyBoots;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class EnchantmentRegistry implements ModRegistry
{
    public static final DeferredRegister<Enchantment> enchantments = DeferredRegister.create(Registries.ENCHANTMENT, AdditionalUtils.MOD_ID);

    public static final DeferredHolder<Enchantment, AU_EfficiencyBoots> BOOT_MAGIC = enchantments.register("boot_magic", AU_EfficiencyBoots::new);

    @Override
    public void register(IEventBus bus)
    {
        enchantments.register(bus);
    }
}
