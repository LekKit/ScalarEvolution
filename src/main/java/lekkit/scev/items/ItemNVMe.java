package lekkit.scev.items;

public class ItemNVMe extends ItemStorage {
    @Override
    public String getStorageOrigin() {
        return "archriscv.img";
    }

    @Override
    public long getStorageSize() {
        return 2048;
    }
}

