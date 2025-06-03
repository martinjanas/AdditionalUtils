package additional_utils.menus.menu.generator;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu>
{
    public GeneratorScreen(GeneratorMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
    {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Draw background panel (dark gray)
        guiGraphics.fill(x, y, x + imageWidth, y + imageHeight, 0xFF202020);

        // Draw energy bar background (black)
        guiGraphics.fill(x + 80, y + 20, x + 96, y + 70, 0xFF000000);

        int currentEnergy = menu.getEnergy();
        int maxEnergy = menu.getMaxEnergy();

        if (maxEnergy > 0)
        {
            int barHeight = (int) ((currentEnergy / (float) maxEnergy) * 50); // Max height: 50px
            int barTop = y + 70 - barHeight;

            // Draw energy bar fill (green)
            guiGraphics.fill(x + 80, barTop, x + 96, y + 70, 0xFF00FF00);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
        guiGraphics.drawString(font, title, 8, 6, 0xFFFFFF, false);

        // Energy label
        int currentEnergy = menu.getEnergy();
        int maxEnergy = menu.getMaxEnergy();

        String energyText = currentEnergy + " / " + maxEnergy + " RF";
        guiGraphics.drawString(font, energyText, 8, 20, 0x00FF00, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

}

