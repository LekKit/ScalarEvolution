package lekkit.scev.render.util;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

import net.minecraft.client.Minecraft;

public class DisplayRenderer {
    /*
     * Renders a framebuffer texture on display
     */
    public static void renderDisplay(int displayTextureID, double leftX, double rightX, double topY, double bottomY, double depthZ) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, displayTextureID);

        renderOverlay(leftX, rightX, topY, bottomY, depthZ);
    }

    public static void renderOverlay(double leftX, double rightX, double topY, double bottomY, double depthZ) {
        depthZ = fixZFighting(depthZ);

        // Full brightness on this thing (Emits glow with shaders)
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.f, 240.f);

        GL11.glDisable(GL11.GL_LIGHTING);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        tessellator.addVertexWithUV( leftX,  bottomY,  depthZ,  0.0, 1.0); // Bottom-left
        tessellator.addVertexWithUV(rightX,  bottomY,  depthZ,  1.0, 1.0); // Bottom-right
        tessellator.addVertexWithUV(rightX,     topY,  depthZ,  1.0, 0.0); // Top-right
        tessellator.addVertexWithUV( leftX,     topY,  depthZ,  0.0, 0.0); // Top-left
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);
    }

    // TODO: Character display (VT) renderer
    public static void renderVT() {
    }

    public static double fixZFighting(double depthZ) {
        if (depthZ < 0.5) {
            depthZ += 0.005;
        } else {
            depthZ -= 0.005;
        }
        return depthZ;
    }
}
