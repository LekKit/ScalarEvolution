package lekkit.scev.container;

import lekkit.scev.tileentity.TileEntityComputerCase;
import lekkit.scev.inventory.SlotBase;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerComputerCase extends ContainerBase {
    public ContainerComputerCase(EntityPlayer player, TileEntityComputerCase inventory) {
        super(player, inventory, true);

        // Motherboard slot
        addSlotToContainer(new SlotBase(inventory, 0, 8, 18, "slot_motherboard"));

        // CPU slot
        addSlotToContainer(new SlotBase(inventory, 1, 80, 36, "slot_cpu"));

        // Firmware flash slot
        addSlotToContainer(new SlotBase(inventory, 2, 110, 102, "slot_flash"));

        // RAM slots
        addSlotToContainer(new SlotBase(inventory, 3, 110, 24, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, 4, 110, 42, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, 5, 110, 60, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, 6, 110, 78, "slot_ram"));

        // NVMe slots
        addSlotToContainer(new SlotBase(inventory, 7, 80, 72, "slot_m2"));
        addSlotToContainer(new SlotBase(inventory, 8, 80, 90, "slot_m2"));

        // PCI slots
        addSlotToContainer(new SlotBase(inventory, 9, 44, 18, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 10, 44, 36, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 11, 44, 54, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 12, 44, 72, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 13, 44, 90, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, 14, 44, 108, "slot_pci"));
    }
}
