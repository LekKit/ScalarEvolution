package lekkit.scev.gui;

import lekkit.scev.container.ContainerMachine;

import lekkit.scev.client.DisplayState;
import lekkit.scev.client.DisplayManager;

import lekkit.scev.gui.util.KeyUtil;

import lekkit.scev.packet.PacketDispatcher;
import lekkit.scev.packet.server.MachineResetPacket;
import lekkit.scev.packet.server.MachineInputPacket;

public class GuiDisplay extends GuiDisplayBase {
    public ContainerMachine containerMachine;

    public GuiDisplay(ContainerMachine container) {
        super(container);
        containerMachine = container;

        setBackgroundTexture("display");
    }

    public void inputPacket(byte inputType, byte key) {
        PacketDispatcher.sendToServer(new MachineInputPacket(inputType, key));
    }

    public void inputPacket(byte inputType, short x, short y) {
        PacketDispatcher.sendToServer(new MachineInputPacket(inputType, x, y));
    }

    @Override
    public void keyboardDown(int keycode) {
        inputPacket(MachineInputPacket.INPUT_KEYBOARD_PRESS, KeyUtil.hidKeyFromLWJGL(keycode));
    }

    @Override
    public void keyboardUp(int keycode) {
        inputPacket(MachineInputPacket.INPUT_KEYBOARD_RELEASE, KeyUtil.hidKeyFromLWJGL(keycode));
    }

    @Override
    public void mouseDown(int btn) {
        inputPacket(MachineInputPacket.INPUT_MOUSE_PRESS, (byte)(1 << btn));
    }

    @Override
    public void mouseUp(int btn) {
        inputPacket(MachineInputPacket.INPUT_MOUSE_RELEASE, (byte)(1 << btn));
    }

    @Override
    public void mousePlace(int x, int y) {
        inputPacket(MachineInputPacket.INPUT_MOUSE_PLACE, (short)x, (short)y);
    }

    @Override
    public void mouseMove(int x, int y) {
        inputPacket(MachineInputPacket.INPUT_MOUSE_MOVE, (short)x, (short)y);
    }

    @Override
    public void mouseScroll(int scrollDelta) {
        inputPacket(MachineInputPacket.INPUT_MOUSE_SCROLL, (byte)scrollDelta);
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
