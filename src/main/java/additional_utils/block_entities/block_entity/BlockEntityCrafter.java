package additional_utils.block_entities.block_entity;

import additional_utils.registries.BlockEntityRegistry;
import additional_utils.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityCrafter extends BlockEntity
{
    private SimpleContainer inv;
    private boolean did_craft = false;
    private int slot_selected = 0;

    //Doesnt work atm
    public BlockEntityCrafter(BlockPos pos, BlockState blockState)
    {
        super(BlockEntityRegistry.crafter.get(), pos, blockState);

        inv = new SimpleContainer(3);
    }

    public void handle_crafting(ItemStack a, ItemStack b, Item output)
    {
        did_craft = false;

        ItemStack input1 = inv.getItem(0); // First input slot
        ItemStack input2 = inv.getItem(1); // Second input slot
        ItemStack outputSlot = inv.getItem(2); // Output slot

        // Ensure both input slots are not empty
        if (input1.isEmpty() || input2.isEmpty())
            return;

        // Check if the inputs match the required items
        if (input1.is(a.getItem()) && input2.is(b.getItem())) {
            // If the output slot is not empty, check if it can stack with the result
            if (!outputSlot.isEmpty() && (!outputSlot.is(output) || outputSlot.getCount() >= outputSlot.getMaxStackSize()))
                return;

            // Decrease input stacks
            input1.shrink(1);
            input2.shrink(1);

            // Add the crafted item to the output slot
            if (outputSlot.isEmpty()) {
                inv.setItem(2, new ItemStack(output, 1)); // Create a new stack in the output slot
            } else {
                outputSlot.grow(1); // Increment the count of the existing stack
            }

            // Set crafting flag to true
            did_craft = true;

            // Mark the block entity as updated
            setChanged();
        }
    }

    public void insert_items(Player player, InteractionHand hand)
    {
        var level = player.level();
        if (level.isClientSide() || hand != InteractionHand.MAIN_HAND)
            return;

        ItemStack hand_stack = player.getItemInHand(hand);
        if (hand_stack.isEmpty())
            return;

        ItemStack stack_copy = hand_stack.copyWithCount(1);

        // Clear inventory if crafting was previously done
        if (did_craft) {
            inv.clearContent();
            did_craft = false; // Reset crafting flag
        }

        // Determine the slot to insert the item
        if (slot_selected == 0) {
            if (inv.getItem(0).isEmpty()) { // Check if the first slot is empty
                inv.setItem(0, stack_copy);
                hand_stack.shrink(1); // Decrease the item count in hand
                slot_selected = 1; // Update to the next slot
            }
        } else if (slot_selected == 1) {
            if (inv.getItem(1).isEmpty()) { // Check if the second slot is empty
                inv.setItem(1, stack_copy);
                hand_stack.shrink(1); // Decrease the item count in hand
                slot_selected = 0; // Loop back to the first slot
            }
        }

        setChanged(); // Mark the block entity as changed for saving
    }

    public void retrieve_crafted_item(Player player, InteractionHand hand)
    {
        var level = player.level();
        if (level.isClientSide() || hand != InteractionHand.MAIN_HAND)
            return;

        ItemStack outputSlot = inv.getItem(2);

        // If the player is shifting and the output slot is not empty
        if (player.isShiftKeyDown() && !outputSlot.isEmpty()) {
            ItemStack remaining = outputSlot.copy();

            // Attempt to add the crafted item to the player's inventory
            if (player.getInventory().add(remaining)) {
                inv.setItem(2, ItemStack.EMPTY); // Clear the output slot
            } else {
                // If not all items could be added, update the output slot with the remainder
                inv.setItem(2, remaining);
            }

            setChanged(); // Notify that the block's data has changed
        }
    }

    public <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T e)
    {
        if (level.isClientSide())
            return;

        handle_crafting(new ItemStack(Items.IRON_INGOT.asItem()), new ItemStack(Items.GOLD_INGOT.asItem()), Items.DIAMOND);
        handle_crafting(new ItemStack(Items.IRON_INGOT.asItem()), new ItemStack(Items.IRON_INGOT.asItem()), Items.GOLD_INGOT);
    }
}
