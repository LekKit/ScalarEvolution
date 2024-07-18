package lekkit.scev.main;

import lekkit.scev.packet.PacketDispatcher;
import lekkit.scev.server.MachineManager;
import lekkit.scev.items.*;
import lekkit.scev.blocks.*;
import lekkit.scev.tileentity.*;
import lekkit.scev.render.item.*;
import lekkit.scev.render.tileentity.*;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.MinecraftForgeClient;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = ScalarEvolution.MODID, version = ScalarEvolution.VERSION, name = ScalarEvolution.NAME)
public class ScalarEvolution {
    @Instance(ScalarEvolution.MODID)
    public static ScalarEvolution instance;

    @SidedProxy(clientSide = "lekkit.scev.main.ClientProxy", serverSide = "lekkit.scev.main.CommonProxy")
    public static CommonProxy proxy;

    public static final String MODID = "scev";
    public static final String NAME = "Scalar Evolution";
    public static final String VERSION = "@VERSION@";

    public static final int GUI_WORKSTATION_INV = 0;
    public static final int GUI_MOTHERBOARD_INV = 1;

    public static ModConfig config;

    public static Block vt100;
    public static Block workstation;
    public static Block powermark;
    public static Block tinkerpad;
    public static Block crt_monitor;
    public static Block keyboard;
    public static Block keyboard_mouse;

    public static Item epoxy;
    public static Item silica_compound;
    public static Item mold_compound;
    public static Item fiberglass;
    public static Item silicon_wafer;
    public static Item pcb_base;
    public static Item dsub_connector;
    public static Item crystal_oscillator;
    public static Item electronic_parts;
    public static Item voltage_regulator;
    public static Item rtc_module;
    public static Item memory_chip;
    public static Item char_display;
    public static Item gfx_display;

    public static Item soldering_iron;

    public static Item flash_chip;
    public static Item hdd;
    public static Item soc;
    public static Item cpu1;
    public static Item cpu2;
    public static Item cpu3;
    public static Item ram_sodimm1;
    public static Item ram_sodimm2;
    public static Item ram_sodimm3;
    public static Item ram_sodimm4;
    public static Item rtl8169;
    public static Item vga_card;
    public static Item gpio_card;
    public static Item sound_card;
    public static Item nvme;
    public static Item motherboard1;
    public static Item motherboard2;
    public static Item motherboard3;

    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderers();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // NO-OP
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config.load(event);

        vt100 = registerTileModel("vt100", new BlockVT100(), ItemBlockBase.class, TileEntityVT100.class,
                                    new TileVT100Renderer(), new ItemModelRenderer("vt100"));
        workstation = registerTileModel("workstation", new BlockWorkstation(), ItemBlockBase.class, TileEntityWorkstation.class,
                                    new TileModelRenderer("workstation", true), new ItemModelRenderer("workstation"));
        powermark = registerTileModel("powermark", new BlockPowermark(), ItemBlockBase.class, TileEntityPowermark.class,
                                    new TileModelRenderer("powermark", true), new ItemModelRenderer("powermark"));
        tinkerpad = registerTileModel("tinkerpad", new BlockTinkerpad(), ItemBlockBase.class, TileEntityTinkerpad.class,
                                    new TileTinkerpadRenderer(), new ItemTinkerpadRenderer());
        crt_monitor = registerTileModel("crt_monitor", new BlockCRT(), ItemBlockBase.class, TileEntityCRT.class,
                                    new TileModelRenderer("crt_monitor", false), new ItemModelRenderer("crt_monitor"));
        keyboard = registerTileModel("keyboard", new BlockKeyboard(), ItemBlockBase.class, TileEntityKeyboard.class,
                                    new TileModelRenderer("keyboard", true), new ItemModelRenderer("keyboard"));
        keyboard_mouse = registerTileModel("keyboard_mouse", new BlockKeyboardMouse(), ItemBlockBase.class, TileEntityKeyboardMouse.class,
                                    new TileModelRenderer("keyboard_mouse", true), new ItemModelRenderer("keyboard_mouse"));

        epoxy = registerItem("epoxy");
        silica_compound = registerItem("silica_compound");
        mold_compound = registerItem("mold_compound");
        fiberglass = registerItem("fiberglass");
        silicon_wafer = registerItem("silicon_wafer");
        pcb_base = registerItem("pcb_base");
        dsub_connector = registerItem("dsub_connector");
        crystal_oscillator = registerItem("crystal_oscillator");
        electronic_parts = registerItem("electronic_parts");
        voltage_regulator = registerItem("voltage_regulator");
        rtc_module = registerItem("rtc_module");
        memory_chip = registerItem("memory_chip");
        char_display = registerItem("char_display");
        gfx_display = registerItem("gfx_display");

        soldering_iron = registerItem("soldering_iron", new ItemSolderingIron());

        flash_chip = registerItem("flash_chip", new ItemFlash());
        soc = registerItem("soc");
        hdd = registerItem("hdd");
        cpu1 = registerItem("cpu1", new ItemCPU());
        cpu2 = registerItem("cpu2", new ItemCPU());
        cpu3 = registerItem("cpu3", new ItemCPU());
        ram_sodimm1 = registerItem("ram_sodimm1", new ItemRAM(0));
        ram_sodimm2 = registerItem("ram_sodimm2", new ItemRAM(1));
        ram_sodimm3 = registerItem("ram_sodimm3", new ItemRAM(2));
        ram_sodimm4 = registerItem("ram_sodimm4", new ItemRAM(3));
        rtl8169 = registerItem("rtl8169", new ItemPCI());
        vga_card = registerItem("vga_card", new ItemPCI());
        gpio_card = registerItem("gpio_card", new ItemPCI());
        sound_card = registerItem("sound_card", new ItemPCI());
        nvme = registerItem("nvme", new ItemNVMe());
        motherboard1 = registerItem("motherboard1", new ItemMotherboard(1));
        motherboard2 = registerItem("motherboard2", new ItemMotherboard(2));
        motherboard3 = registerItem("motherboard3", new ItemMotherboard(3));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        PacketDispatcher.registerPackets();

        // Recipes
        GameRegistry.addRecipe(new ItemStack(epoxy), "SRS", "RPR", "SRS",
            'S', Items.sugar, 'R', Items.reeds, 'P', Items.potionitem);

        GameRegistry.addRecipe(new ItemStack(silica_compound, 16), "SOS", "OEO", "SOS",
            'S', Blocks.sand, 'E', epoxy, 'O', Blocks.obsidian);

        GameRegistry.addRecipe(new ItemStack(fiberglass), "GGG", "EEE", "GGG",
            'G', Blocks.glass_pane, 'E', epoxy);

        GameRegistry.addRecipe(new ItemStack(pcb_base), "NRN", "FFF", "NRN",
            'N', Items.gold_nugget, 'R', Items.redstone, 'F', fiberglass);

        GameRegistry.addRecipe(new ItemStack(crystal_oscillator), "III", "MQM", "R R",
            'I', Items.iron_ingot, 'M', mold_compound, 'Q', Items.quartz, 'R', Items.redstone);

        GameRegistry.addRecipe(new ItemStack(dsub_connector), "INI", " M ", "   ",
            'I', Items.iron_ingot, 'N', Items.gold_nugget, 'M', mold_compound);

        GameRegistry.addRecipe(new ItemStack(char_display), "MRM", "GSL", "MNM",
            'M', mold_compound, 'R', Items.redstone, 'G', Blocks.glass_pane,
            'S', Items.sign, 'L', Blocks.redstone_lamp, 'N', Items.gold_nugget);

        GameRegistry.addRecipe(new ItemStack(gfx_display), "MRM", "GDL", "MNM",
            'M', mold_compound, 'R', Blocks.redstone_block, 'G', Blocks.glass_pane,
            'D', Items.diamond, 'L', Blocks.redstone_lamp, 'N', Items.gold_nugget);

        GameRegistry.addRecipe(new ItemStack(motherboard1), " V ", "DPC", "OES",
            'V', voltage_regulator, 'D', dsub_connector, 'P', pcb_base, 'C', rtc_module,
            'O', crystal_oscillator, 'E', electronic_parts, 'S', new ItemStack(soldering_iron, 1, 32767));


        GameRegistry.addSmelting(silica_compound, new ItemStack(mold_compound), 0.0F);
        GameRegistry.addSmelting(soldering_iron, new ItemStack(soldering_iron), 0.0F);
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        System.out.println("Finishing all machines...");
        MachineManager.finishAllMachines();
    }

    protected static Block registerTileModel(String name, Block block, java.lang.Class<? extends ItemBlock> itemClass,
                                            Class<? extends TileEntity> teClass, TileEntitySpecialRenderer teRenderer,
                                            IItemRenderer itemRenderer) {
        block.setBlockName(ScalarEvolution.MODID + "." + name).setCreativeTab(ScalarEvolution.creativeTab);
        GameRegistry.registerBlock(block, itemClass, name);
        GameRegistry.registerTileEntity(teClass, ScalarEvolution.MODID + ":" + name);
        ClientRegistry.bindTileEntitySpecialRenderer(teClass, teRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block), itemRenderer);
        return block;
    }

    protected static Item registerItem(String itemName, Item item) {
        item.setUnlocalizedName(ScalarEvolution.MODID + "." + itemName).setTextureName(ScalarEvolution.MODID + ":" + itemName);
        item.setCreativeTab(creativeTab);
        GameRegistry.registerItem(item, itemName);
        return item;
    }

    protected static Item registerItem(String itemName) {
        return registerItem(itemName, new ItemBase());
    }

    public static CreativeTabs creativeTab = new CreativeTabs("tabScalarEvolution") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(vt100);
        }
    };
}
