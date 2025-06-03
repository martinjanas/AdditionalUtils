package additional_utils.menus.menu.generator;

import additional_utils.api.inventory_containers.BarrelContainer;
import additional_utils.api.slot.BarrelSlot;
import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.block_entities.block_entity.BlockEntityGenerator;
import additional_utils.registries.BlockRegistry;
import additional_utils.registries.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GeneratorMenu extends AbstractContainerMenu
{
    private final BlockEntityGenerator block_entity;
    private final ContainerData data;

    private BlockPos pos;

    //Client constructor
    public GeneratorMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer)
    {
        super(MenuRegistry.generator_menu.get(), containerId);

        BlockPos pos = buffer.readBlockPos();
        BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(pos);

        this.block_entity = blockEntity instanceof BlockEntityGenerator ? (BlockEntityGenerator) blockEntity : null;

        this.data = new SimpleContainerData(2);
        addDataSlots(data);
    }

    //Server constructor
    public GeneratorMenu(int container_id, Inventory player_inventory, BlockEntityGenerator block_entity)
    {
        super(MenuRegistry.generator_menu.get(), container_id);
        this.block_entity = block_entity;

        this.data = new SimpleContainerData(2); // [0] = current, [1] = max

        addDataSlots(data);

        data.set(0, (int) block_entity.energy.getEnergyStored());
        data.set(1, (int) block_entity.energy.getMaxEnergyStored());
    }

    public GeneratorMenu(int containerId, Inventory playerInventory, BlockEntityGenerator blockEntity, BlockPos pos)
    {
        this(containerId, playerInventory, blockEntity);
        this.pos = pos;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(ContainerLevelAccess.NULL, player, BlockRegistry.generator.get());
    }

    @Override
    public void broadcastChanges()
    {
        super.broadcastChanges();

        if (block_entity != null)
        {
            data.set(0, (int) block_entity.energy.getEnergyStored());
            data.set(1, (int) block_entity.energy.getMaxEnergyStored());
        }
    }

    public int getEnergy()
    {
        return data.get(0);
    }

    public int getMaxEnergy()
    {
        return data.get(1);
    }
}
