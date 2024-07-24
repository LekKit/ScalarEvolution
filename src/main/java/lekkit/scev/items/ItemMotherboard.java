package lekkit.scev.items;

import lekkit.scev.main.ScalarEvolution;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemMotherboard extends ItemNonStackable {
    private final int level;

    public ItemMotherboard(int level) {
        this.level = level;

        addLore("text.scev.ram_slots: §e" + getRAMSlots());
        addLore("text.scev.pci_slots: §e" + getPCISlots());
        addLore("text.scev.m2_slots: §e" + getM2Slots());
    }

    public int getMotherboardLevel() {
        return level;
    }

    public int getRAMSlots() {
        switch (getMotherboardLevel()) {
            case 1: return 2;
            case 2: return 3;
            case 3: return 4;
        }
        // gtfo
        return 0;
    }

    public int getPCISlots() {
        switch (getMotherboardLevel()) {
            case 1: return 2;
            case 2: return 4;
            case 3: return 6;
        }
        return 0;
    }

    public int getM2Slots() {
        switch (getMotherboardLevel()) {
            case 1: return 1;
            case 2: return 1;
            case 3: return 2;
        }
        return 0;
    }

    public boolean isInventorySlotEnabled(int slotIndex) {
        switch (slotIndex) {
            // CPU slot
            case 0:
            // Flash slot
            case 1:
            // RAM slots
            case 2:
            case 3: return getMotherboardLevel() >= 1;
            // RAM slots (lvl 2)
            case 4: return getMotherboardLevel() >= 2;
            // RAM slots (lvl 3)
            case 5: return getMotherboardLevel() >= 3;
            // M.2 slots
            case 6: return getMotherboardLevel() >= 1;
            // M.2 slots (lvl 3)
            case 7: return getMotherboardLevel() >= 3;
            // PCI slots
            case 8:
            case 9: return getMotherboardLevel() >= 1;
            // PCI slots (lvl 2)
            case 10:
            case 11: return getMotherboardLevel() >= 2;
            // PCI slots (lvl3)
            case 12:
            case 13: return getMotherboardLevel() >= 3;
        }
        return false;
    }

    // Without this, inventory won't work (Go figure...)
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (!world.isRemote && !player.isSneaking()) {
            player.openGui(ScalarEvolution.instance, ScalarEvolution.GUI_MOTHERBOARD_INV, world, 0, 0, 0);
        }

        return itemstack;
    }
}

