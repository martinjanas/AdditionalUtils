package additional_utils.api.packet;

import additional_utils.AdditionalUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUpdatePacket implements CustomPacketPayload
{
    public static final ResourceLocation ID = new ResourceLocation(AdditionalUtils.MOD_ID, "inventory_update");

    private final int containerId;
    private final List<ItemStack> items;

    public InventoryUpdatePacket(int containerId, List<ItemStack> items)
    {
        this.containerId = containerId;
        this.items = items;
    }

    public InventoryUpdatePacket(FriendlyByteBuf buf)
    {
        this.containerId = buf.readVarInt();
        int size = buf.readVarInt();
        this.items = new ArrayList<>();

        for (int i = 0; i < size; i++)
        {
            ItemStack stack = buf.readItem();
            int realCount = buf.readVarInt(); // Read actual count
            stack.setCount(realCount); // Ensure proper stack size
            this.items.add(stack);
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(containerId);
        buf.writeVarInt(items.size());

        for (ItemStack item : items) {
            buf.writeItem(item);
            buf.writeVarInt(item.getCount()); // Write actual stack count as an int
        }
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public int getContainerId() {
        return containerId;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}

