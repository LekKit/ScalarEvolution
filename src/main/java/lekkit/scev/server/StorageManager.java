package lekkit.scev.server;

import java.util.UUID;
import java.io.File;
import java.io.RandomAccessFile;

public class StorageManager {
    static {
        File dir = new File("./scev");
        dir.mkdir();
        dir = new File("./scev/images");
        dir.mkdir();
        dir = new File("./scev/snapshots");
        dir.mkdir();
    }

    public static boolean initImage(UUID image_uuid, long image_mb) {
        try {
            File file = new File(imagePath(image_uuid));
            if (file.createNewFile()) {
                RandomAccessFile sparse_file = new RandomAccessFile(file, "r+");
                sparse_file.setLength(image_mb << 20);
                sparse_file.close();
            }
            return file.isFile();
        } catch (Exception e) {
            System.out.println("IO error!");
        }
        return false;
    }

    public static String imagePath(UUID image_uuid) {
        return "./scev/images/" + image_uuid.toString() + ".img";
    }

    public static String snapshotPath(UUID machine_uuid) {
        return "./scev/snapshots/" + machine_uuid.toString() + ".img";
    }
}
