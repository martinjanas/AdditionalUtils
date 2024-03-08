package additional_utils.block_entities.block_entity;

import additional_utils.block_entities.BlockEntityRegister;
import additional_utils.blocks.block.BlockStackCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.UUID;

public class BlockEntityStackCounter extends BlockEntity
{
    public BlockEntityStackCounter(BlockPos pos, BlockState state)
    {
        super(BlockEntityRegister.block_entity_stack_counter.get(), pos, state);
    }

    private int stack_count;

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T e)
    {
        if (true)
            return;

        if (level.isClientSide())
            return;

        if (!(e instanceof BlockEntityStackCounter entity))
            return;

        BlockStackCounter block_stack_counter = (BlockStackCounter)state.getBlock();

        if (block_stack_counter.block_owner_uuid == null)
            return;

        UUID block_owner_id = block_stack_counter.block_owner_uuid;

        Player player = level.getPlayerByUUID(block_owner_id);
    }

    private static void OnPlayerInteract(PlayerInteractEvent.RightClickBlock event)
    {
        Level level = event.getLevel();

        if (level == null)
            return;

        if (level.isClientSide() || event.getHand() != InteractionHand.MAIN_HAND)
            return;

        BlockEntity block_entity = level.getBlockEntity(event.getPos());

        if (!(block_entity instanceof BlockEntityStackCounter entity))
            return;

        BlockStackCounter block_stack_counter = (BlockStackCounter)entity.getBlockState().getBlock();

        if (block_stack_counter.block_owner_uuid == null)
            return;

        UUID block_owner_uuid = block_stack_counter.block_owner_uuid;

        Player player = level.getPlayerByUUID(block_owner_uuid);

        if (player == null)
            return;

        ItemStack stack_in_hand = player.getItemInHand(InteractionHand.MAIN_HAND);

        final boolean is_shifting = player.isShiftKeyDown();

        if (is_shifting && stack_in_hand.is(Items.DIAMOND))
        {
            entity.stack_count++;
            stack_in_hand.shrink(1);

            if (true)
                System.out.printf("Depositing Items, stack_count: %d\n", entity.stack_count);
        }

        if (!is_shifting && entity.stack_count > 0)
        {
            entity.stack_count--;
            player.addItem(new ItemStack(Items.DIAMOND, 1));

            if (true)
                System.out.printf("Withdrawing Items, stack_count: %d\n", entity.stack_count);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event)
    {
        OnPlayerInteract(event);
    }
}
