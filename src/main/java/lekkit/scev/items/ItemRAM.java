package lekkit.scev.items;

public class ItemRAM extends ItemBase {
    private final int level;

    public ItemRAM(int level) {
        this.level = level;

        addLore("text.scev.capacity", ": §e" + getRamMegs() + " MiB");
    }

    public int getRamMegs() {
        switch (level) {
            case 0: return 8;
            case 1: return 16;
            case 2: return 32;
            case 3: return 64;
        }
        return 0;
    }
}
