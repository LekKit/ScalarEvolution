package lekkit.scev.gui;

import lekkit.scev.container.ContainerTileDummy;

public class GuiDisplayTile extends GuiDisplayBase {
    public GuiDisplayTile(ContainerTileDummy container) {
        super(container);

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
        super.drawUserInterface();
        //renderTexturedGuiRect(16 + 10, 16 + 10, 640 - 50, 480 - 50);
    }
}
