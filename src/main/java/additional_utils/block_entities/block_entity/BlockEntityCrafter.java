package additional_utils.block_entities.block_entity;

import additional_utils.menus.menu.crafter.CrafterMenu;
import additional_utils.registries.BlockEntityRegistry;
import additional_utils.registries.RecipeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


public class BlockEntityCrafter extends BlockEntity implements MenuProvider
{
    private final SimpleContainer inventory = new SimpleContainer(3);

    private int craftingTickCounter = 0;

    public BlockEntityCrafter(BlockPos pos, BlockState blockState)
    {
        super(BlockEntityRegistry.crafter.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        ListTag inventoryList = new ListTag();

        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty())
            {
                CompoundTag itemTag = new CompoundTag();
                stack.save(itemTag);
                inventoryList.add(itemTag);
            }
        }

        tag.put("crafter_inventory", inventoryList);

        setChanged();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        if (tag.contains("crafter_inventory", Tag.TAG_LIST))
        {
            ListTag inventoryList = tag.getList("crafter_inventory", Tag.TAG_COMPOUND);

            for (int i = 0; i < Math.min(inventoryList.size(), inventory.getContainerSize()); i++)
            {
                CompoundTag itemTag = inventoryList.getCompound(i);
                ItemStack stack = ItemStack.of(itemTag);
                inventory.setItem(i, stack);
            }
        }

        setChanged();
    }

    public void handle_crafting()
    {
        Level level = getLevel();
        if (level == null)
            return;

        RecipeManager recipe_manager = level.getRecipeManager();

        RecipeRegistry.crafter_type.asOptional().flatMap(crafterRecipeType -> recipe_manager.getRecipeFor(crafterRecipeType, inventory, level)).ifPresent(recipe ->
        {
            ItemStack outputSlot = inventory.getItem(2);

            if (outputSlot.isEmpty())
                inventory.setItem(2, recipe.value().getResultItem(level.registryAccess()).copy());
            else outputSlot.grow(1);

            inventory.getItem(0).shrink(1);
            inventory.getItem(1).shrink(1);

            setChanged();
        });
    }


    public <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T e)
    {
        if (level.isClientSide())
            return;

        craftingTickCounter++;

        if (craftingTickCounter >= 80)
        {
            handle_crafting();
            craftingTickCounter = 0;
        }
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Crafter");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player)
    {
        return new CrafterMenu(id, player_inventory, inventory);
    }
}
