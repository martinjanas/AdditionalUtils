package additional_utils.registries;

import additional_utils.AdditionalUtils;
import additional_utils.blocks.block.BlockCrafter;
import additional_utils.blocks.block.BlockHealer;
import additional_utils.blocks.block.BlockStackCounter;
import additional_utils.registries.impl.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry implements ModRegistry
{
    public static DeferredRegister.Blocks blocks = DeferredRegister.createBlocks(AdditionalUtils.MOD_ID);

    public static DeferredBlock<Block> healer;
    public static DeferredBlock<Block> stack_counter;
    public static DeferredBlock<Block> crafter;

    @Override
    public void register(IEventBus bus)
    {
        healer = blocks.register("healer", () -> new BlockHealer(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
        stack_counter = blocks.register("stack_counter", BlockStackCounter::new);
        crafter = blocks.register("crafter", () -> new BlockCrafter(BlockBehaviour.Properties.of().strength(50f, 1200f)));

        blocks.register(bus);
    }
}
