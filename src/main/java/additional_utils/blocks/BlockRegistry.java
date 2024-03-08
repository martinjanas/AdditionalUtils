package additional_utils.blocks;

import additional_utils.AdditionalUtils;
import additional_utils.blocks.block.BlockHealer;
import additional_utils.blocks.block.BlockStackCounter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry
{
    public static DeferredRegister.Blocks mod_blocks = DeferredRegister.createBlocks(AdditionalUtils.MOD_ID);

    public static DeferredBlock<Block> healer_block;
    public static DeferredBlock<Block> stack_counter_block;

    public void register()
    {
        healer_block = mod_blocks.register("healer_block", () -> new BlockHealer(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
        stack_counter_block = mod_blocks.register("stack_counter_block", BlockStackCounter::new);
    }
}
