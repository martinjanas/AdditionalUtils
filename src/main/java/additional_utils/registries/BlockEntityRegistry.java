package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry implements ModRegistry
{
    public static final DeferredRegister<BlockEntityType<?>> block_entities = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AdditionalUtils.MOD_ID);

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityStackCounter>> stack_counter;

    @Override
    public void register(IEventBus bus)
    {
        stack_counter = block_entities.register("stack_counter", () -> BlockEntityType.Builder.of(BlockEntityStackCounter::new, BlockRegistry.stack_counter.get()).build(null));
        
        block_entities.register(bus);
    }
}
