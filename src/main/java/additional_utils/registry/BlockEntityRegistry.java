package additional_utils.registry;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.registry.impl.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry implements ModRegistry
{
    public static final DeferredRegister<BlockEntityType<?>> mod_block_entities = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AdditionalUtils.MOD_ID);

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityStackCounter>> be_stack_counter;

    public void register()
    {
        be_stack_counter = mod_block_entities.register("be_stack_counter", () -> BlockEntityType.Builder.of(BlockEntityStackCounter::new, BlockRegistry.stack_counter.get()).build(null));
    }

    @Override
    public void register_to_bus(IEventBus bus)
    {
        mod_block_entities.register(bus);
    }
}
