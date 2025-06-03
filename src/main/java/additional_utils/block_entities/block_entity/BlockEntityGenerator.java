package additional_utils.block_entities.block_entity;

import additional_utils.api.energy_storage.MyEnergyStorage;
import additional_utils.menus.menu.generator.GeneratorMenu;
import additional_utils.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockEntityGenerator extends BlockEntity implements MenuProvider
{
    private static int max_energy = 100000;
    private static int rf_per_tick = 25;

        public final MyEnergyStorage energy = new MyEnergyStorage(max_energy, rf_per_tick, 0)
        {
            @Override
            public int extractEnergy(int maxExtract, boolean simulate)
            {
                int extracted = super.extractEnergy(maxExtract, simulate);
                if (extracted > 0 && !simulate)
                    setChanged();

                return extracted;
            }
        };

    public BlockEntityGenerator(BlockPos pos, BlockState blockState)
    {
        super(BlockEntityRegistry.generator.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putInt("EnergyStored", energy.getEnergyStored());

        setChanged();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        if (tag.contains("EnergyStored", 3)) // 3 = int tag
        {
            energy.SetEnergy(tag.getInt("EnergyStored"));
        }
        setChanged();
    }

    public <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T e)
    {
        if (level.isClientSide())
            return;

        if ((energy.getEnergyStored() + rf_per_tick) <= energy.getMaxEnergyStored())
        {
            energy.receiveEnergy(rf_per_tick, false);
            setChanged();
        }
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player pPlayer)
    {
        return new GeneratorMenu(id, player_inventory, this, worldPosition);
    }
}
