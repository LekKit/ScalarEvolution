package lekkit.scev.render.item;

import lekkit.scev.gui.MachineGui;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import net.minecraft.client.renderer.OpenGlHelper;

import net.minecraft.client.renderer.Tessellator;

public class ItemTinkerpadRenderer extends ItemModelRenderer {
    public ItemTinkerpadRenderer() {
        super("tinkerpad");
    }

    @Override
    public void renderDecorations(ItemRenderType type, ItemStack item, Object... data) {
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
