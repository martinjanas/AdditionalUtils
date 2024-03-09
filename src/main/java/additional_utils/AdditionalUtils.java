package additional_utils;

import additional_utils.registry.BlockEntityRegistry;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.registry.BlockRegistry;
import additional_utils.registry.CreativeTabRegistry;
import additional_utils.registry.BlockItemRegistry;
import additional_utils.registry.ItemRegistry;
import additional_utils.items.item.ItemSolidifiedXP;
import additional_utils.registry.impl.ModRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.List;

@Mod(AdditionalUtils.MOD_ID)
public class AdditionalUtils
{
    public static final String MOD_ID = "adutils";
    private static final Logger LOGGER = LogUtils.getLogger();

    //TODO: Introduce global event manager?

    public AdditionalUtils(IEventBus bus)
    {
        List<ModRegistry> mod_registries = List.of(new ItemRegistry(), new BlockRegistry(), new BlockItemRegistry(),
                new BlockEntityRegistry(), new CreativeTabRegistry());

        bus.addListener(this::common_setup);
        NeoForge.EVENT_BUS.addListener(ItemSolidifiedXP::onEntityDropEvent);
        NeoForge.EVENT_BUS.addListener(BlockEntityStackCounter::onPlayerInteract);
        
        for (ModRegistry registry : mod_registries)
        {
            registry.register();
            registry.register_to_bus(bus);
        }
    }

    private void common_setup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
}
