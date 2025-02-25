package additional_utils.menus.menu.impl;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class BasicScreen extends AbstractContainerScreen<AbstractContainerMenu>
{

    public BasicScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init()
    {
        super.init();
        // This is where you would add buttons, elements, or any interactive components
        // For example:
        // addRenderableWidget(new Button(this.width / 2 - 100, this.height / 2, 200, 20, Component.literal("Click Me!"), button -> { /* action */ }));
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        // Draw the sprite at (0, 0) or wherever you need the background to be
        // You can change the width and height to fill the screen (e.g. 176x166 is the size of a default inventory background)
        //graphics.blit(new ResourceLocation("textures/gui/container/inventory"), 0, 0, 0, 0, 176, 166);
    }


//    @Override
//    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
//    {
//        super.render(graphics, mouseX, mouseY, partialTick);  // Render base screen
//        this.renderLabels(graphics, mouseX, mouseY);  // Render additional labels
//    }

//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button)
//    {
//        // Handle mouse clicks if needed
//        // Example: return super.mouseClicked(mouseX, mouseY, button);
//        return super.mouseClicked(mouseX, mouseY, button);
//    }
}
