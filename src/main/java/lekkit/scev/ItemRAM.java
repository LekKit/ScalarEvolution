package lekkit.scev;

public class ItemRAM extends ItemBase {
    private final int level;

    public ItemRAM(int level) {
        this.level = level;

        addLore("text.scev.capacity", ": §e" + (getRamSize() >> 20) + " MiB");
    }

    public int getRamSize() {
        switch (level) {
            case 0: return (8 << 20);
            case 1: return (16 << 20);
            case 2: return (32 << 20);
            case 3: return (64 << 20);
        }
        return 0;
    }
}
