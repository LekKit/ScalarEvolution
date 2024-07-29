package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ButtonBase extends GuiButton {
    protected final ResourceLocation btnTexture;

    public ButtonBase(int id, int x, int y, String texture) {
        super(id, x, y, 18, 18, "");
        this.enabled = true;
        this.visible = true;
        btnTexture = new ResourceLocation(ScalarEvolution.MODID, "textures/gui/" + texture + ".png");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(btnTexture);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, 1.0F);
            if (mouseX >= this.xPosition && mouseY >= this.yPosition
             && mouseX < this.xPosition + this.width
             && mouseY < this.yPosition + this.height) {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(xPosition, yPosition + height, 0.0, 0.0, 1.0);
            tessellator.addVertexWithUV(xPosition + width, yPosition + height, 0.0, 1.0, 1.0);
            tessellator.addVertexWithUV(xPosition + width, yPosition, 0.0, 1.0, 0.0);
            tessellator.addVertexWithUV(xPosition, yPosition, 0.0, 0.0, 0.0);
            tessellator.draw();
        }
    }

    @Override
    public void mouseReleased(int par1, int par2) {
    }

}
