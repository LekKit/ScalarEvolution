package lekkit.scev.render.tileentity;

import lekkit.scev.main.ScalarEvolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import org.lwjgl.opengl.GL11;

public class TileRendererBase extends TileEntitySpecialRenderer {
    private ResourceLocation texture;
    private WavefrontObject model;
    private boolean fatModel;

    public TileRendererBase(String modelName, boolean fatModel) {
        if (modelName != null) {
            ResourceLocation resModel = new ResourceLocation(ScalarEvolution.MODID, "models/" + modelName + ".obj");
            this.texture = new ResourceLocation(ScalarEvolution.MODID, "textures/blocks/" + modelName + ".png");
            this.model = (WavefrontObject)AdvancedModelLoader.loadModel(resModel);
        } else {
            this.texture = null;
            this.model = null;
        }
        this.fatModel = ScalarEvolution.config.fatModels ? true : fatModel;
    }

    public TileRendererBase(boolean fatModel) {
        this(null, fatModel);
    }

    public TileRendererBase() {
        this(true);
    }

    public void renderTileEntity(TileEntity tileEntity) {
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        int metadata = tileEntity.getBlockMetadata();

        GL11.glPushMatrix();
        if (fatModel) {
            int direction = ((metadata + 2) / 4) % 4;
            GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
            GL11.glRotatef(direction * 90F, 0F, 1F, 0F);
        } else {
            GL11.glTranslated(x + 0.5, y + 0.375, z + 0.5);
            GL11.glScalef(0.75f, 0.75f, 0.75f);
            GL11.glRotatef(metadata * 22.5f, 0F, 1F, 0F);
        }

        if (model != null) {
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            model.renderAll();
        }

        renderTileEntity(tileEntity);

        GL11.glPopMatrix();
    }
}
