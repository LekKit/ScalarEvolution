package lekkit.scev.main;

import lekkit.scev.server.MachineManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.client.event.GuiOpenEvent;

import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;

import net.minecraft.client.Minecraft;

public class ModEventHandler {
    public void onGamePaused() {
        System.out.println("Pausing all machines...");
        MachineManager.pauseAllMachines();
    }
    public void onGameUnpaused() {
        System.out.println("Resuming all machines...");
        MachineManager.unpauseAllMachines();
    }

    protected boolean paused = false;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.isSingleplayer() && mc.getIntegratedServer() != null) {
            if (!paused && event.gui != null && event.gui.doesGuiPauseGame() && !mc.getIntegratedServer().getPublic()) {
                onGamePaused();
                paused = true;
            } else if (paused) {
                onGameUnpaused();
                paused = false;
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        System.out.println("Destroyed item " + event.original.getDisplayName());
    }

    @SubscribeEvent
    public void onItemExpire(ItemExpireEvent event) {
        System.out.println("Destroyed item " + event.entityItem.getEntityItem().getDisplayName());
    }
}
