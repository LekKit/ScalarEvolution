package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.container.ContainerBase;
import lekkit.scev.inventory.SlotBase;
import lekkit.scev.inventory.IconSilly;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.client.resources.I18n;
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
        return guiLeft;
    }

    public int getGuiPosY() {
        return guiTop;
    }

    public ContainerBase getClientSideContainer() {
        return container;
    }

    public void initUserInterface() {
    }

    public boolean isSlotEnabled(int slotIndex) {
        return true;
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

    /*
     * "mouse in zone" method used by isMouseOverSlot() which is private... So ugh anyways
     *
     * - YOU ARE SURROUNDED! USE MIXINS!
     * - I WILL NOT USE MIXINS!
     */
    @Override
    protected boolean func_146978_c(int x, int y, int w, int h, int mouseX, int mouseY) {
        for (int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i) {
            Slot slot = (Slot)this.inventorySlots.inventorySlots.get(i);

            if (slot.xDisplayPosition == x && slot.yDisplayPosition == y && slot instanceof SlotBase) {
                SlotBase slotBase = (SlotBase)slot;
                if (!isSlotEnabled(slotBase.getSlotIndex())) return false;
            }
        }
        return super.func_146978_c(x, y, w, h, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        if (bgTexture != null) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(bgTexture);
            drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        }

        // Because fuck minecraft GUI slot background rendering
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);
        for (int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i) {
            Slot slot = (Slot)this.inventorySlots.inventorySlots.get(i);
            if (slot instanceof SlotBase) {
                SlotBase slotBase = (SlotBase)slot;
                String slotBgName = slotBase.getSlotBackgroundName();

                if (isSlotEnabled(slotBase.getSlotIndex()) && slotBase.getStack() == null && slotBgName != null) {
                    int x = guiLeft + slotBase.xDisplayPosition;
                    int y = guiTop + slotBase.yDisplayPosition;
                    ResourceLocation res = new ResourceLocation(ScalarEvolution.MODID, "textures/gui/" + slotBgName + ".png");

                    mc.getTextureManager().bindTexture(res);
                    drawTexturedModelRectFromIcon(x, y, new IconSilly(), 16, 16);
                }
            }
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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

            this.fontRendererObj.drawStringWithShadow(containerName, 8, 6, getGuiTextColor());
            this.fontRendererObj.drawStringWithShadow(inventoryName, 8, this.ySize - 93, getGuiTextColor());
        }
    }
}
