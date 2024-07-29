package lekkit.scev.main;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import lekkit.scev.blocks.BlockDirectionalModel;
import lekkit.scev.render.block.*;
import lekkit.scev.render.item.*;
import lekkit.scev.render.tileentity.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        registerBlockRenderer("workstation", new BlockModelRenderer("workstation", true), new ItemModelRenderer("workstation"));
        registerBlockRenderer("powermark", new BlockModelRenderer("powermark", true), new ItemModelRenderer("powermark"));
        registerBlockRenderer("crt_monitor", new BlockModelRenderer("crt_monitor", false), new ItemModelRenderer("crt_monitor"));
        registerBlockRenderer("keyboard", new BlockModelRenderer("keyboard", true), new ItemModelRenderer("keyboard"));
        registerBlockRenderer("keyboard_mouse", new BlockModelRenderer("keyboard_mouse", true), new ItemModelRenderer("keyboard_mouse"));

        registerBlockRenderer("vt100", new BlockModelRenderer("vt100", false));
        registerTileRenderer("vt100", new TileVT100Renderer(), new ItemModelRenderer("vt100"));

        registerBlockRenderer("tinkerpad", new BlockModelRenderer("tinkerpad", false));
        registerTileRenderer("tinkerpad", new TileTinkerpadRenderer(), new ItemTinkerpadRenderer());
    }


    public void registerBlockRenderer(String name, ISimpleBlockRenderingHandler blockRenderer) {
        if (blockRenderer != null && ScalarEvolution.getBlock(name) instanceof BlockDirectionalModel) {
            BlockDirectionalModel blockModel = (BlockDirectionalModel)ScalarEvolution.getBlock(name);
            RenderingRegistry.registerBlockHandler(blockRenderer);
            blockModel.setRenderId(blockRenderer.getRenderId());
        }
    }

    public void registerTileRenderer(String name, TileEntitySpecialRenderer tileRenderer) {
        if (tileRenderer != null) {
            ClientRegistry.bindTileEntitySpecialRenderer(ScalarEvolution.getTileClass(name), tileRenderer);
        }
    }

    public void registerItemRenderer(String name, IItemRenderer itemRenderer) {
        if (itemRenderer != null) {
            MinecraftForgeClient.registerItemRenderer(ScalarEvolution.getItem(name), itemRenderer);
        }
    }

    public void registerItemBlockRenderer(String name, IItemRenderer itemRenderer) {
        if (itemRenderer != null) {
            MinecraftForgeClient.registerItemRenderer(ScalarEvolution.getItemBlock(name), itemRenderer);
        }
    }

    public void registerBlockRenderer(String name, ISimpleBlockRenderingHandler blockRenderer, TileEntitySpecialRenderer tileRenderer) {
        registerBlockRenderer(name, blockRenderer);
        registerTileRenderer(name, tileRenderer);
    }

    public void registerBlockRenderer(String name, ISimpleBlockRenderingHandler blockRenderer, IItemRenderer itemRenderer) {
        registerBlockRenderer(name, blockRenderer);
        registerItemBlockRenderer(name, itemRenderer);
    }

    public void registerBlockRenderer(String name, ISimpleBlockRenderingHandler blockRenderer,
                                      TileEntitySpecialRenderer tileRenderer, IItemRenderer itemRenderer) {
        registerBlockRenderer(name, blockRenderer);
        registerTileRenderer(name, tileRenderer);
        registerItemBlockRenderer(name, itemRenderer);
    }

    public void registerTileRenderer(String name, TileEntitySpecialRenderer tileRenderer, IItemRenderer itemRenderer) {
        registerTileRenderer(name, tileRenderer);
        registerItemBlockRenderer(name, itemRenderer);
    }


}
