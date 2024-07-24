package lekkit.scev.gui;

import lekkit.scev.container.ContainerTileDummy;

public class GuiDisplayTile extends GuiDisplayBase {
    public GuiDisplayTile(ContainerTileDummy container) {
        super(container);

        setBackgroundTexture("display");
    }

    @Override
    public void initUserInterface() {
        setGuiSize(640 + 32, 480 + 80);

        buttonList.add(new ButtonBase(1, getGuiPosX() + guiCoord(620), getGuiPosY() + guiCoord(532), "power_btn"));
        buttonList.add(new ButtonBase(2, getGuiPosX() + guiCoord(560), getGuiPosY() + guiCoord(532), "reset_btn"));
    }

    @Override
    public void drawUserInterface() {
        renderTexturedGuiRect(getGuiPosX(), getGuiPosY(), getGuiWidth(), getGuiHeight());
    }
}
