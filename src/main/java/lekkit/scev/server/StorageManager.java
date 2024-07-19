package lekkit.scev.server;

import java.util.UUID;
import java.io.File;
import java.nio.file.Files;
import java.nio.channels.FileChannel;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;
import java.util.EnumSet;

public class StorageManager {
    static {
        File dir = new File("./scev");
        dir.mkdir();
        dir = new File("./scev/assets");
        dir.mkdir();
        dir = new File("./scev/images");
        dir.mkdir();
        dir = new File("./scev/snapshots");
        dir.mkdir();
    }

    public static boolean checkImage(UUID image_uuid) {
        File file = new File(imagePath(image_uuid));
        return file.isFile();
    }

    public static boolean createImage(UUID image_uuid, long image_mb) {
        try {
            FileChannel channel = FileChannel.open(Paths.get(imagePath(image_uuid)),
                EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.SPARSE, StandardOpenOption.WRITE));
            channel.truncate(image_mb << 20);
            boolean ret = channel.size() == image_mb << 20;
            channel.close();
            return ret;
        } catch (Throwable e) {
            System.out.println("IO error!");
        }
        return false;
    }

    public static boolean copyImage(UUID image_uuid, String origin) {
        if (System.getProperty("os.name").startsWith("Linux")) {
            try {
                Runtime.getRuntime().exec("cp --reflink=always " + assetPath(origin) + " " + imagePath(image_uuid)).waitFor();
            } catch (Throwable e) {}
        }

        if (!checkImage(image_uuid)) {
            try {
                Files.copy(Paths.get(assetPath(origin)), Paths.get(imagePath(image_uuid)), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (Throwable e) {}
        }
        return false;
    }

    public static boolean initImage(UUID image_uuid, long image_mb, String origin) {
        if (checkImage(image_uuid)) return true;

        if (origin != null) {
            return copyImage(image_uuid, origin);
        } else {
            return createImage(image_uuid, image_mb);
        }
    }

    public static String assetPath(String asset) {
        return "./scev/assets/" + asset;
    }

    public static String imagePath(UUID image_uuid) {
        return "./scev/images/" + image_uuid.toString() + ".img";
    }

    public static String snapshotPath(UUID machine_uuid) {
        return "./scev/snapshots/" + machine_uuid.toString() + ".img";
    }
}
