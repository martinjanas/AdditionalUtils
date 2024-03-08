package additional_utils.registry;

import additional_utils.AdditionalUtils;
import additional_utils.blocks.block.BlockHealer;
import additional_utils.blocks.block.BlockStackCounter;
import additional_utils.registry.impl.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry implements ModRegistry
{
    public static DeferredRegister.Blocks mod_blocks = DeferredRegister.createBlocks(AdditionalUtils.MOD_ID);

    public static DeferredBlock<Block> healer;
    public static DeferredBlock<Block> stack_counter;

    public void register()
    {
        healer = mod_blocks.register("healer", () -> new BlockHealer(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
        stack_counter = mod_blocks.register("stack_counter", BlockStackCounter::new);
    }

    @Override
    public void register_to_bus(IEventBus bus)
    {
        mod_blocks.register(bus);
    }
}
