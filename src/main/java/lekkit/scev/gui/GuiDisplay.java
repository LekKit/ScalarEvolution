package lekkit.scev.gui;

import lekkit.scev.container.ContainerMachine;

import lekkit.scev.client.DisplayState;
import lekkit.scev.client.DisplayManager;

import lekkit.scev.packet.PacketDispatcher;
import lekkit.scev.packet.server.MachineResetPacket;

public class GuiDisplay extends GuiDisplayBase {
    public ContainerMachine containerMachine;

    public GuiDisplay(ContainerMachine container) {
        super(container);
        containerMachine = container;

        setBackgroundTexture("display");
    }

    @Override
    public void mousePlace(int x, int y) {
        System.out.println("place: " + x + ", " + y);
    }

    @Override
    public void mouseMove(int x, int y) {
        System.out.println("move: " + x + ", " + y);
    }

    @Override
    public void initUserInterface() {
        setGuiSize(640 + 32, 480 + 32);

        if (containerMachine.getMachineHandle() != null) {
            buttonList.add(new ButtonBase(1, guiCoordX(620), guiCoordY(520), "power_btn"));
            buttonList.add(new ButtonBase(2, guiCoordX(540), guiCoordY(520), "reset_btn"));
        }
    }

    @Override
    public void drawUserInterface() {
        DisplayState display = DisplayManager.getDisplayState(containerMachine.getMachineUUID());
        super.drawUserInterface();

        if (display != null) {
            display.bindTexture();
            renderTexturedGuiRect(16, 16, 640, 480);
        }
    }


    @Override
    public void buttonClicked(ButtonBase button) {
        switch (button.id) {
            case 1:
                PacketDispatcher.sendToServer(new MachineResetPacket(false));
                break;
            case 2:
                PacketDispatcher.sendToServer(new MachineResetPacket(true));
                break;
        }
    }
}
