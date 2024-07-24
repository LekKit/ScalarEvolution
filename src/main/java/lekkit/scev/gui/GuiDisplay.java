package lekkit.scev.gui;

import lekkit.scev.container.ContainerDisplayDummy;
import lekkit.scev.client.DisplayState;
import lekkit.scev.client.DisplayManager;

public class GuiDisplay extends GuiDisplayBase {
    public ContainerDisplayDummy cont;

    public GuiDisplay(ContainerDisplayDummy container) {
        super(container);
        cont = container;

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

        buttonList.add(new ButtonBase(1, guiCoordX(620), guiCoordY(520), "power_btn"));
        buttonList.add(new ButtonBase(2, guiCoordX(540), guiCoordY(520), "reset_btn"));
    }

    @Override
    public void drawUserInterface() {
        DisplayState display = DisplayManager.getDisplayState(cont.getMachineUUID());
        super.drawUserInterface();

        if (display != null) {
            display.bindTexture();
            renderTexturedGuiRect(16, 16, 640, 480);
        }
    }
}
