package lekkit.scev.items;

public class ItemFlash extends ItemStorage {
    @Override
    public String getStorageOrigin() {
        return "fw_payload.bin";
    }

    @Override
    public long getStorageSize() {
        return 8;
    }
}
