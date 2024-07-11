package lekkit.scev.inventory;

import net.minecraft.util.IIcon;

public class IconSilly implements IIcon {
    public String getIconName() {
        return "";
    }

    public int getIconWidth() {
        return 16;
    }

    public int getIconHeight() {
        return 16;
    }

    public float getMinU() {
        return 0.f;
    }

    public float getMinV() {
        return 0.f;
    }

    public float getMaxU() {
        return 1.f;
    }

    public float getMaxV() {
        return 1.f;
    }

    public float getInterpolatedU(double val) {
        return 0.f;
    }

    public float getInterpolatedV(double val) {
        return 0.f;
    }
}
