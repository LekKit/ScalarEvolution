package lekkit.scev.items;

public class ItemNVMe extends ItemStorage {
    @Override
    public String getStorageOrigin() {
        return "rootfs.ext2";
    }

    @Override
    public long getStorageSize() {
        return 2048;
    }
}

