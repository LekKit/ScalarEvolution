package lekkit.scev.render.tileentity;

import lekkit.scev.gui.MachineGui;

import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

import net.minecraft.client.Minecraft;

public class TileVT100Renderer extends TileModelRenderer {
    public TileVT100Renderer() {
        super("vt100", false);
    }

    @Override
    public void renderDecorations(TileEntity tileEntity, double x, double y, double z, float f) {
        // Full brightness on this thing (Emits glow with shaders)
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.f, 240.f);

        GL11.glDisable(GL11.GL_LIGHTING);

        // Some test crap
        /*
        GL11.glPushMatrix();
        GL11.glScalef(0.01f, 0.01f, 0.01f);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Text test aaaAAA", 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
        */
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, MachineGui.texID);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        tessellator.addVertexWithUV( 0.375,    -0.25,   -0.126,  0.0, 1.0); // Bottom-left
        tessellator.addVertexWithUV(-0.1875,   -0.25,   -0.126,  1.0, 1.0); // Bottom-right
        tessellator.addVertexWithUV(-0.1875,    0.1875, -0.126,  1.0, 0.0); // Top-right
        tessellator.addVertexWithUV( 0.375,     0.1875, -0.126,  0.0, 0.0); // Top-left
        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
    }
}
