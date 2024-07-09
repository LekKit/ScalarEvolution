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

        addLore("text.scev.ram_slots", ": §e" + getRAMSlots());
        addLore("text.scev.pci_slots", ": §e" + getPCISlots());
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

