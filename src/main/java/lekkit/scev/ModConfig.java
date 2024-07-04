package lekkit.scev;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ModConfig {
    private static final String GENERIC_CONFIG = "Generic Config";
    private static final String VISUAL_CONFIG = "Visual Settings";

    public static boolean fatModels = false;

    public static int jitCache = 16;
    public static boolean jit = true;

    public static void load(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "ScalarEvolution.cfg"), "0.1", true);
        config.load();

        config.addCustomCategoryComment(GENERIC_CONFIG, "Config for the Scalar Evolution mod, created by LekKit.");

        jit = config.getBoolean("jit", GENERIC_CONFIG, true,
                "Enable JIT (Disable only if you have issues)");
        jitCache = config.getInt("jitCache", GENERIC_CONFIG, 16, 1, 64,
                "Per-core JIT cache amount (MB)");


        config.addCustomCategoryComment(VISUAL_CONFIG, "Visual Settings");

        fatModels = config.getBoolean("fatModels", VISUAL_CONFIG, false,
                "Always draw fat 4-directional full block models");

        config.save();
    }

}
