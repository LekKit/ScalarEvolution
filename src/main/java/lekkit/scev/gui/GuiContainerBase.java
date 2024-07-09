package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.container.ContainerBase;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiContainerBase extends GuiContainer {
    private final boolean fatGui;
    private ResourceLocation bgTexture = null;
    private ContainerBase container;

    public GuiContainerBase(ContainerBase container, boolean fatGui) {
        super(container);
        this.container = container;
        this.fatGui = fatGui;

        // Fucking magic numbers calculated from GuiChest behavior, go figure
        if (fatGui) {
            this.ySize = 222;
        } else {
            this.ySize = 168;
        }
    }

    public void setBackgroundTexture(String textureName) {
        bgTexture = new ResourceLocation(ScalarEvolution.MODID, "textures/gui/" + textureName + ".png");
    }

    public int getGuiPosX() {
        return (this.width - this.xSize) / 2;
    }

    public int getGuiPosY() {
        return (this.height - this.ySize) / 2;
    }

    public ContainerBase getClientSideContainer() {
        return container;
    }

    public void initUserInterface() {
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonList.clear();

        initUserInterface();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        if (bgTexture != null) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(bgTexture);
            int k = getGuiPosX();
            int l = getGuiPosY();
            if (fatGui) {
                this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 125);
                this.drawTexturedModalRect(k, l + 125, 0, 126, this.xSize, 96);
            } else {
                drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
            }
        }
    }
}
