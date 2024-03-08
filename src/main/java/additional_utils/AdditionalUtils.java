package additional_utils;

import additional_utils.block_entities.BlockEntityRegistry;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.blocks.BlockRegistry;
import additional_utils.creative_tabs.CreativeTabRegistry;
import additional_utils.items.BlockItemRegistry;
import additional_utils.items.ItemRegistry;
import additional_utils.items.item.ItemSolidifiedXP;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(AdditionalUtils.MOD_ID)
public class AdditionalUtils
{
    public static final String MOD_ID = "additional_utils";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ItemRegistry item_registry = new ItemRegistry();
    public static final BlockRegistry block_registry = new BlockRegistry();
    public static final BlockItemRegistry block_item_registry = new BlockItemRegistry();
    public static final BlockEntityRegistry block_entity_registry = new BlockEntityRegistry();
    public static final CreativeTabRegistry creative_tab_registry = new CreativeTabRegistry();

    public AdditionalUtils(IEventBus bus)
    {
        bus.addListener(this::common_setup);
        NeoForge.EVENT_BUS.addListener(ItemSolidifiedXP::onEntityDropEvent);
        NeoForge.EVENT_BUS.addListener(BlockEntityStackCounter::onPlayerInteract);

        item_registry.register();
        block_registry.register();
        block_item_registry.register();
        block_entity_registry.register();
        creative_tab_registry.register();

        ItemRegistry.mod_items.register(bus);
        BlockRegistry.mod_blocks.register(bus);
        BlockEntityRegistry.mod_block_entities.register(bus);
        CreativeTabRegistry.mod_tabs.register(bus);
    }

    private void common_setup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
}
