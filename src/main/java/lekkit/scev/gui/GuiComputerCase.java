package lekkit.scev.gui;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.container.ContainerComputerCase;
import lekkit.scev.tileentity.TileEntityComputerCase;
import lekkit.scev.items.ItemMotherboard;

public class GuiComputerCase extends GuiContainerBase {

    public GuiComputerCase(ContainerComputerCase container) {
        super(container);

        setBackgroundTexture("computer_case_empty");
    }

    public TileEntityComputerCase getComputerCase() {
        ContainerComputerCase container = (ContainerComputerCase)getClientSideContainer();
        return (TileEntityComputerCase)container.getContainerInventory();
    }

    @Override
    public void updateScreen() {
        ItemMotherboard motherboard = getComputerCase().getItemMotherboard();
        if (motherboard == null) {
            setBackgroundTexture("computer_case_empty");
        } else {
            setBackgroundTexture("computer_case_motherboard" + motherboard.getMotherboardLevel());
        }
    }

    @Override
    public boolean isSlotEnabled(int slotIndex) {
        int computerCaseSize = getComputerCase().getComputerCaseSize();
        if (slotIndex < computerCaseSize) {
            return true;
        } else {
            ItemMotherboard motherboard = getComputerCase().getItemMotherboard();
            if (motherboard != null) {
                return motherboard.isInventorySlotEnabled(slotIndex - computerCaseSize);
            }
        }
        return false;
    }

    @Override
    public void initUserInterface() {
        buttonList.add(new ButtonBase(1, getGuiPosX() + 7, getGuiPosY() + 52, "power_btn"));
        buttonList.add(new ButtonBase(2, getGuiPosX() + 7, getGuiPosY() + 74, "reset_btn"));
    }
}
