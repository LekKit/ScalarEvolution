package lekkit.scev.main;

import lekkit.scev.render.block.*;
import lekkit.scev.render.tileentity.*;
import lekkit.scev.render.item.*;

import lekkit.scev.blocks.BlockDirectionalModel;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.IItemRenderer;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        registerBlockRenderer("workstation", new BlockModelRenderer("workstation", true), new ItemModelRenderer("workstation"));
        registerBlockRenderer("powermark", new BlockModelRenderer("powermark", true), new ItemModelRenderer("powermark"));
        registerBlockRenderer("crt_monitor", new BlockModelRenderer("crt_monitor", false), new ItemModelRenderer("crt_monitor"));
        registerBlockRenderer("keyboard", new BlockModelRenderer("keyboard", true), new ItemModelRenderer("keyboard"));
        registerBlockRenderer("keyboard_mouse", new BlockModelRenderer("keyboard_mouse", true), new ItemModelRenderer("keyboard_mouse"));

        registerTileRenderer("vt100", new TileVT100Renderer(), new ItemModelRenderer("vt100"));
        registerTileRenderer("tinkerpad", new TileTinkerpadRenderer(), new ItemTinkerpadRenderer());
    }

    public void registerBlockRenderer(String name, ISimpleBlockRenderingHandler blockRenderer, IItemRenderer itemRenderer) {
        if (ScalarEvolution.getBlock(name) instanceof BlockDirectionalModel) {
            BlockDirectionalModel blockModel = (BlockDirectionalModel)ScalarEvolution.getBlock(name);
            RenderingRegistry.registerBlockHandler(blockRenderer);
            blockModel.setRenderId(blockRenderer.getRenderId());
        }
        if (itemRenderer != null) {
            MinecraftForgeClient.registerItemRenderer(ScalarEvolution.getItemBlock(name), itemRenderer);
        }
    }

    public void registerBlockRenderer(String name, ISimpleBlockRenderingHandler blockRenderer) {
        registerBlockRenderer(name, blockRenderer, null);
    }

    public void registerTileRenderer(String name, TileEntitySpecialRenderer tileRenderer, IItemRenderer itemRenderer) {
        ClientRegistry.bindTileEntitySpecialRenderer(ScalarEvolution.getTileClass(name), tileRenderer);
        if (itemRenderer != null) {
            MinecraftForgeClient.registerItemRenderer(ScalarEvolution.getItemBlock(name), itemRenderer);
        }
    }

    public void registerTileRenderer(String name, TileEntitySpecialRenderer tileRenderer) {
        registerTileRenderer(name, tileRenderer, null);
    }

    public void registerItemRenderer(String name, IItemRenderer itemRenderer) {
        MinecraftForgeClient.registerItemRenderer(ScalarEvolution.getItem(name), itemRenderer);
    }

    public void registerItemBlockRenderer(String name, IItemRenderer itemRenderer) {
        MinecraftForgeClient.registerItemRenderer(ScalarEvolution.getItemBlock(name), itemRenderer);
    }
}
