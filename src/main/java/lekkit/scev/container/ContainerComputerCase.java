package lekkit.scev.container;

import lekkit.scev.server.IMachineHandle;
import lekkit.scev.inventory.SlotBase;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerComputerCase extends ContainerMachine {
    public ContainerComputerCase(EntityPlayer player, IMachineHandle inventory) {
        super(player, inventory, true);

        int caseSlots = inventory.getComputerCaseSize();

        // Motherboard slot
        addSlotToContainer(new SlotBase(inventory, 0, 8, 18, "slot_motherboard"));

        // CPU slot
        addSlotToContainer(new SlotBase(inventory, caseSlots + 0, 80, 36, "slot_cpu"));

        // Firmware flash slot
        addSlotToContainer(new SlotBase(inventory, caseSlots + 1, 110, 102, "slot_flash"));

        // RAM slots
        addSlotToContainer(new SlotBase(inventory, caseSlots + 2, 110, 24, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 3, 110, 42, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 4, 110, 60, "slot_ram"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 5, 110, 78, "slot_ram"));

        // NVMe slots
        addSlotToContainer(new SlotBase(inventory, caseSlots + 6, 80, 72, "slot_m2"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 7, 80, 90, "slot_m2"));

        // PCI slots
        addSlotToContainer(new SlotBase(inventory, caseSlots + 8, 44, 18, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 9, 44, 36, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 10, 44, 54, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 11, 44, 72, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 12, 44, 90, "slot_pci"));
        addSlotToContainer(new SlotBase(inventory, caseSlots + 13, 44, 108, "slot_pci"));
    }
}
