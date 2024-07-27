package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.container.ContainerBase;
import lekkit.scev.gui.util.LocaleUtil;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GuiDisplayBase extends GuiScreen {
    protected final ContainerBase container;

    private ResourceLocation bgTexture = null;

    // Everything here is in unscaled vanilla GUI coords
    private int guiLeft = 0;
    private int guiTop = 0;
    private int xSize = 0;
    private int ySize = 0;
    private int guiScale = 1;

    private boolean mouseInGui = false;
    private boolean lockMouse = false;
    private int lastMouseX = -1;
    private int lastMouseY = -1;

    public GuiDisplayBase(ContainerBase container) {
        this.container = container;
    }

    public int getMinecraftGuiScale() {
        int scale = (Display.getWidth() + 20) / width;
        if (scale == 0) scale = 1;
        return scale;
    }

    public void setGuiSize(int w, int h) {
        guiScale = getMinecraftGuiScale();
        while (w / guiScale > width || h / guiScale > height) {
            guiScale *= 2;
        }

        this.xSize = w / guiScale;
        this.ySize = h / guiScale;

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
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
        return guiLeft * guiScale;
    }

    public int getGuiPosY() {
        return guiTop * guiScale;
    }

    public int getGuiWidth() {
        return xSize * guiScale;
    }

    public int getGuiHeight() {
        return ySize * guiScale;
    }

    public int getScreenWidth() {
        return width * guiScale;
    }

    public int getScreenHeight() {
        return height * guiScale;
    }

    public int guiCoord(int coord) {
        return coord / guiScale;
    }

    public int guiCoordX(int coord) {
        return guiLeft + guiCoord(coord);
    }

    public int guiCoordY(int coord) {
        return guiTop + guiCoord(coord);
    }

    public int displayCoord(int coord) {
        return coord * guiScale / getMinecraftGuiScale();
    }

    public void renderTexturedGuiRect(int x, int y, int w, int h) {
        double l = (double)guiCoordX(x);
        double r = (double)guiCoordX(x + w);
        double t = (double)guiCoordY(y);
        double b = (double)guiCoordY(y + h);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(l, b, 0.0, 0.0, 1.0); // Bottom-left
        tessellator.addVertexWithUV(r, b, 0.0, 1.0, 1.0); // Bottom-right
        tessellator.addVertexWithUV(r, t, 0.0, 1.0, 0.0); // Top-right
        tessellator.addVertexWithUV(l, t, 0.0, 0.0, 0.0); // Top-left
        tessellator.draw();
    }

    public void renderText(String text, int x, int y, int color) {
        this.fontRendererObj.drawStringWithShadow(text, guiCoordX(x), guiCoordY(y), color);
    }

    public void renderText(String text, int x, int y) {
        renderText(text, x, y, 0xE0E0E0);
    }

    public void mouseLock(boolean lock) {
        if (Mouse.isCreated()) {
            if (Mouse.isGrabbed() != lock) Mouse.setGrabbed(lock);
        }
    }

    public boolean needsMouse() {
        return true;
    }

    public void initUserInterface() {}

    public void drawUserInterface() {
        renderText(LocaleUtil.translate("text.scev.send_esc_hint"), 0, getGuiHeight() + 8);
        if (needsMouse()) {
            renderText(LocaleUtil.translate("text.scev.lock_mouse_hint"), 0, getGuiHeight() + 40);
        }
    }

    public void mouseMove(int x, int y) {}

    public void mousePlace(int x, int y) {}

    public void mouseDown(int button) {}

    public void mouseUp(int button) {}

    public void mouseScroll(int scrollDelta) {}

    public void keyboardDown(int lwjglKeycode) {}

    public void keyboardUp(int lwjglKeycode) {}

    public void buttonClicked(ButtonBase button) {}

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        if (bgTexture != null) {
            this.mc.getTextureManager().bindTexture(bgTexture);
            renderTexturedGuiRect(0, 0, getGuiWidth(), getGuiHeight());
        }

        drawUserInterface();

        super.drawScreen(mouseX, mouseY, partialTicks);
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
        super.onGuiClosed();

        if (this.mc.thePlayer != null) {
            this.container.onContainerClosed(this.mc.thePlayer);
        }

        mouseLock(false);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();

        if (!needsMouse()) return;

        if (lockMouse) {
            // TODO: Test this
            int x = Mouse.getDX();
            int y = -Mouse.getDY();
            if (x != 0 || y != 0) {
                mouseMove(x, y);
            }
            mouseInGui = true;
        } else {
            // LWJGL EventY() goes up
            int x = displayCoord(Mouse.getEventX()) - getGuiPosX();
            int y = displayCoord(Display.getHeight() - Mouse.getEventY()) - getGuiPosY();
            mouseInGui = x >= 0 && x < getGuiWidth() && y >= 0 && y < getGuiHeight();
            if (mouseInGui && (x != lastMouseX || y != lastMouseY)) {
                lastMouseX = x;
                lastMouseY = y;
                mousePlace(x, y);
            }
        }

        if (mouseInGui) {
            int btn = Mouse.getEventButton();
            if (btn != -1) {
                if (Mouse.getEventButtonState()) {
                    mouseDown(btn);
                } else {
                    mouseUp(btn);
                }
            }

            if (Mouse.hasWheel()) {
                int scrollDelta = Mouse.getDWheel();
                if (scrollDelta != 0) {
                    mouseScroll(scrollDelta);
                }
            }
        }
    }

    @Override
    public void handleKeyboardInput() {
        int key = Keyboard.getEventKey();

        if (Keyboard.isRepeatEvent()) {
            return;
        }

        if (Keyboard.getEventKeyState()) {
            if (key == Keyboard.KEY_ESCAPE && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                super.handleKeyboardInput();
                return;
            }

            if (key == Keyboard.KEY_F && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                lockMouse = !lockMouse;
                mouseLock(lockMouse);
            }

            keyboardDown(key);
        } else {
            keyboardUp(key);
        }
    }
}
