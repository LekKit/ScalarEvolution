package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.container.ContainerBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiDisplayBase extends GuiScreen {
    private ResourceLocation bgTexture = null;

    protected int guiLeft = 0;
    protected int guiTop = 0;
    protected int xSize = 0;
    protected int ySize = 0;
    protected int guiScale = 0;

    protected final boolean vanillaGuiScaling = true;

    protected final ContainerBase container;

    public GuiDisplayBase(ContainerBase container) {
        this.container = container;
    }

    public void setBackgroundTexture(String textureName) {
        if (textureName != null) {
            bgTexture = new ResourceLocation(ScalarEvolution.MODID, "textures/gui/" + textureName + ".png");
        } else {
            bgTexture = null;
        }
    }

    public ContainerBase getClientSideContainer() {
        return container;
    }

    public int getGuiPosX() {
        return guiLeft;
    }

    public int getGuiPosY() {
        return guiTop;
    }

    public int getGuiWidth() {
        return xSize;
    }

    public int getGuiHeight() {
        return ySize;
    }

    public int guiCoord(int coord) {
        if (vanillaGuiScaling) {
            return coord / guiScale;
        }
        return coord;
    }

    public void renderTexturedGuiRect(int x, int y, int w, int h) {
        double l = (double)x;
        double r = (double)(x + w);
        double t = (double)y;
        double b = (double)(y + h);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(l, b, 0.0, 0.0, 1.0); // Bottom-left
        tessellator.addVertexWithUV(r, b, 0.0, 1.0, 1.0); // Bottom-right
        tessellator.addVertexWithUV(r, t, 0.0, 1.0, 0.0); // Top-right
        tessellator.addVertexWithUV(l, t, 0.0, 0.0, 0.0); // Top-left
        tessellator.draw();
    }

    public void initUserInterface() {}

    public void drawUserInterface() {}

    public void buttonClicked(ButtonBase button) {}

    public void setGuiSize(int w, int h) {
        guiScale = 1;
        while (w / guiScale > width || h / guiScale > height) {
            guiScale *= 2;
        }

        if (vanillaGuiScaling) {
            this.xSize = w / guiScale;
            this.ySize = h / guiScale;

            this.guiLeft = (this.width - this.xSize) / 2;
            this.guiTop = (this.height - this.ySize) / 2;
        } else {
            this.xSize = w;
            this.ySize = h;

            this.guiLeft = (this.width * guiScale - this.xSize) / 2;
            this.guiTop = (this.height * guiScale - this.ySize) / 2;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        if (!vanillaGuiScaling) {
            GL11.glPushMatrix();
            GL11.glScalef(1.f / guiScale, 1.f / guiScale, 1.f);
        }

        if (bgTexture != null) {
            this.mc.getTextureManager().bindTexture(bgTexture);
            renderTexturedGuiRect(guiLeft, guiTop, xSize, ySize);
        }

        drawUserInterface();

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (!vanillaGuiScaling) {
            GL11.glPopMatrix();
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.openContainer = this.container;
        buttonList.clear();
        setGuiSize(640, 480);

        initUserInterface();
    }

    @Override
    public void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button instanceof ButtonBase) {
            buttonClicked((ButtonBase)button);
        } else {
            System.out.println("Unknown GuiButton clicked!");
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.thePlayer != null) {
            this.container.onContainerClosed(this.mc.thePlayer);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
            this.mc.thePlayer.closeScreen();
        }
    }
}
