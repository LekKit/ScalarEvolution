package lekkit.scev.render.tileentity;

import lekkit.scev.main.ScalarEvolution;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class TileModelRenderer extends TileEntitySpecialRenderer {
    private ResourceLocation texture;
    private WavefrontObject model;
    private boolean fatModel;

    public TileModelRenderer(String modelName, boolean fatModel) {
        ResourceLocation resModel = new ResourceLocation(ScalarEvolution.MODID, "models/" + modelName + ".obj");
        this.texture = new ResourceLocation(ScalarEvolution.MODID, "textures/blocks/" + modelName + ".png");
        this.model = (WavefrontObject)AdvancedModelLoader.loadModel(resModel);
        this.fatModel = ScalarEvolution.config.fatModels ? true : fatModel;
    }

    public void renderDecorations(TileEntity tileEntity, double x, double y, double z, float f) {
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        if (fatModel) {
            int direction = ((tileEntity.getBlockMetadata() + 2) / 4) % 4;
            GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
            GL11.glRotatef(direction * 90F, 0F, 1F, 0F);
        } else {
            GL11.glTranslated(x + 0.5, y + 0.375, z + 0.5);
            GL11.glScalef(0.75f, 0.75f, 0.75f);
            GL11.glRotatef(tileEntity.getBlockMetadata() * 22.5f, 0F, 1F, 0F);
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.renderAll();

        renderDecorations(tileEntity, x, y, z, f);

        GL11.glPopMatrix();
    }
}
