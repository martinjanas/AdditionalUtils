package additional_utils;

import additional_utils.api.packet.ClientInventoryHandler;
import additional_utils.api.packet.InventoryUpdatePacket;
import additional_utils.block_entities.block_entity.BlockEntityBarrel;
import additional_utils.block_entity_renderers.BlockBarrelRenderer;
import additional_utils.menus.menu.barrel.BarrelScreen;
import additional_utils.menus.menu.crafter.CrafterScreen;
import additional_utils.menus.menu.generator.GeneratorScreen;
import additional_utils.registries.*;
import additional_utils.registries.impl.ModRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;

import java.util.List;
import java.util.Random;

@Mod(AdditionalUtils.MOD_ID)
public class AdditionalUtils
{
    public static final String MOD_ID = "adutils";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AdditionalUtils(IEventBus bus)
    {
        List<ModRegistry> mod_registries = List.of(new ItemRegistry(), new BlockRegistry(), new BlockItemRegistry(),
                new BlockEntityRegistry(), new CreativeTabRegistry(), new MenuRegistry(), new RecipeRegistry());

        bus.addListener(this::common_setup);
        bus.addListener(this::client_setup);
        bus.addListener(this::OnRegisterScreens);
        bus.addListener(this::OnRegisterPackets);
        bus.addListener(this::OnRegisterBlockEntityRenderers);
        bus.addListener(this::OnRegisterCapabilities);

        for (ModRegistry registry : mod_registries)
             registry.register(bus);

        //NeoForge.EVENT_BUS.register(EventManager.class);
    }

    public static void sendInventoryUpdate(ServerPlayer player)
    {
        InventoryUpdatePacket packet = new InventoryUpdatePacket(player.containerMenu.containerId, player.containerMenu.getItems());
        PacketDistributor.PLAYER.with(player).send(packet);
    }

    public static void SendInventoryUpdate(ServerPlayer player)
    {
        int containerId = player.containerMenu.containerId;
        int stateId = player.containerMenu.getStateId();
        int slotIndex = 0; // Make sure this is a valid slot

        var items = player.containerMenu.getItems();
        if (slotIndex >= 0 && slotIndex < items.size())
        {
            ItemStack stack = items.get(slotIndex);

            // Send the update packet to sync this slot
            var packet = new ClientboundContainerSetSlotPacket(containerId, stateId, slotIndex, stack);
            player.connection.send(packet);
        }
    }

    private static void handleClient(InventoryUpdatePacket packet, PlayPayloadContext context)
    {
        context.workHandler().execute(() -> ClientInventoryHandler.handleInventoryUpdate(packet));
    }

    @SubscribeEvent
    private void OnRegisterPackets(RegisterPayloadHandlerEvent event)
    {
        event.registrar(AdditionalUtils.MOD_ID).play(InventoryUpdatePacket.ID, InventoryUpdatePacket::new,
                (packet, context) -> handleClient(packet, context));
    }

    @SubscribeEvent
    private void OnRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.barrel.get(), (be, side) -> be.getItemHandler(side));

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, BlockEntityRegistry.generator.get(), (be, side) -> be.energy);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void OnRegisterScreens(RegisterMenuScreensEvent event)
    {
        event.register(MenuRegistry.crafter_menu.get(), CrafterScreen::new);
        event.register(MenuRegistry.barrel_menu.get(), BarrelScreen::new);
        event.register(MenuRegistry.generator_menu.get(), GeneratorScreen::new);
    }

    private void client_setup(final FMLClientSetupEvent event)
    {

    }

    @SubscribeEvent
    public void OnRegisterBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(BlockEntityRegistry.barrel.get(), BlockBarrelRenderer::new);
    }

    private void common_setup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
}
