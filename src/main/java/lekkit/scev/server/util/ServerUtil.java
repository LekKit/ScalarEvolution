package lekkit.scev.server.util;

public class ServerUtil {

    public static boolean runningOnServer() {
        String threadName = Thread.currentThread().getName();
        return (threadName.contains("Server") || threadName.contains("server"))
            && !(threadName.contains("Client") && threadName.contains("client"));
    }
}
