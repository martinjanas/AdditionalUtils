package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.block_entities.block_entity.BlockEntityCrafter;
import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.block_entities.block_entity.BlockEntityGenerator;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry implements ModRegistry
{
    public static final DeferredRegister<BlockEntityType<?>> block_entities = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AdditionalUtils.MOD_ID);

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityCrafter>> crafter;
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityBarrel>> barrel;
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityGenerator>> generator;

    @Override
    public void register(IEventBus bus)
    {
        crafter = block_entities.register("crafter", () -> BlockEntityType.Builder.of(BlockEntityCrafter::new, BlockRegistry.crafter.get()).build(null));
        barrel = block_entities.register("barrel", () -> BlockEntityType.Builder.of(BlockEntityBarrel::new, BlockRegistry.barrel.get()).build(null));
        generator = block_entities.register("generator", () -> BlockEntityType.Builder.of(BlockEntityGenerator::new, BlockRegistry.generator.get()).build(null));

        block_entities.register(bus);
    }
}
