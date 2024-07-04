package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiMotherboard extends GuiContainer {
    private static final ResourceLocation bgTexture = new ResourceLocation(ScalarEvolution.MODID, "textures/gui/motherboard.png");

    public GuiMotherboard(Container container) {
        super(container);
    }

    @Override
    public void initGui() {
        int guiX = (width - 178) / 2;
        int guiY = (height - 168) / 2;
        super.initGui();

        buttonList.clear();
        buttonList.add(new ButtonBase(1, guiX + 8, guiY + 33, "power_btn"));
        buttonList.add(new ButtonBase(2, guiX + 8, guiY + 52, "reset_btn"));
    }
/*
    public void drawScreen(int width, int height, float par3) {
        super.drawScreen(width, height, par3);
    }
*/
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(bgTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
