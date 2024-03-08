package additional_utils.block_entities;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityStackCounter;
import additional_utils.blocks.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry
{
    public static final DeferredRegister<BlockEntityType<?>> mod_block_entities = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AdditionalUtils.MOD_ID);

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityStackCounter>> block_entity_stack_counter;

    public void register()
    {
        block_entity_stack_counter = mod_block_entities.register("be_healer", () -> BlockEntityType.Builder.of(BlockEntityStackCounter::new, BlockRegistry.stack_counter_block.get()).build(null));
    }
}
