package lekkit.scev;

import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

public class TileTinkerpadRenderer extends TileModelRenderer {
    public TileTinkerpadRenderer() {
        super("tinkerpad", false);
    }

    @Override
    public void renderDecorations(TileEntity tileEntity, double x, double y, double z, float f) {
        // Full brightness on this thing (Emits glow with shaders)
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.f, 240.f);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, MachineGui.texID);

        GL11.glDisable(GL11.GL_LIGHTING);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        tessellator.addVertexWithUV( 0.4375,   -0.3125,  0.4374,  0.0, 1.0); // Bottom-left
        tessellator.addVertexWithUV(-0.4375,   -0.3125,  0.4374,  1.0, 1.0); // Bottom-right
        tessellator.addVertexWithUV(-0.4375,    0.4375,  0.4374,  1.0, 0.0); // Top-right
        tessellator.addVertexWithUV( 0.4375,    0.4375,  0.4374,  0.0, 0.0); // Top-left
        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
    }
}
