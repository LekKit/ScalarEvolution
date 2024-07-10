package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.container.ContainerBase;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiContainerBase extends GuiContainer {
    private ResourceLocation bgTexture = null;
    private ContainerBase container;

    public GuiContainerBase(ContainerBase container) {
        super(container);
        this.container = container;

        // Fucking magic numbers calculated from GuiChest behavior, go figure
        if (container.isFatGui()) {
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

    // Return -1 to stop drawing GUI text
    public int getGuiTextColor() {
        return 0xE0E0E0;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        if (bgTexture != null) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(bgTexture);
            int k = getGuiPosX();
            int l = getGuiPosY();
            if (container.isFatGui()) {
                this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 125);
                this.drawTexturedModalRect(k, l + 125, 0, 126, this.xSize, 96);
            } else {
                drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        if (getGuiTextColor() != -1) {
            IInventory containerInventory = container.getContainerInventory();
            IInventory playerInventory = container.getPlayerInventory();
            String containerName = containerInventory.getInventoryName();
            String inventoryName = playerInventory.getInventoryName();
            if (!containerInventory.hasCustomInventoryName()) containerName = I18n.format(containerName);
            if (!playerInventory.hasCustomInventoryName()) inventoryName = I18n.format(inventoryName);

            this.fontRendererObj.drawString(containerName, 8, 6, getGuiTextColor());
            this.fontRendererObj.drawString(inventoryName, 8, this.ySize - 94, getGuiTextColor());
        }
    }
}
