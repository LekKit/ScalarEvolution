package lekkit.scev.client;

import java.util.UUID;
import java.util.HashMap;

import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;

public class DisplayManager {
    protected static HashMap<UUID, DisplayState> displays = new HashMap<UUID, DisplayState>();

    public synchronized static DisplayState createDisplayState(UUID machineUUID, int width, int height) {
        DisplayState display = getDisplayState(machineUUID);

        if (display != null && (display.getWidth() != width || display.getHeight() != height)) {
            destroyDisplayState(machineUUID);
            display = null;
        }

        if (display == null) {
            display = new DisplayState(machineUUID, width, height);
            displays.put(machineUUID, display);
        }

        return display;
    }

    public synchronized static DisplayState getDisplayState(UUID machineUUID) {
        DisplayState display = displays.get(machineUUID);

        // Singleplayer display optimization
        if (display != null && !display.isValid()) {
            destroyDisplayState(machineUUID);
            display = null;
        }

        try {
            if (display == null) {
                MachineState state = MachineManager.getMachineState(machineUUID);
                if (state != null && state.getDisplay() != null) {
                    // This is a singleplayer game, machine is directly accessible
                    display = new DisplayState(state);
                    displays.put(machineUUID, display);
                }
            }
        } catch (Throwable e) {}

        return display;
    }

    public synchronized static void destroyDisplayState(UUID machineUUID) {
        DisplayState display = displays.get(machineUUID);

        if (display != null) {
            display.destroy();
            displays.remove(machineUUID);
        }
    }

    public synchronized static void recycleDisplays() {
        for (HashMap.Entry<UUID, DisplayState> entry : displays.entrySet()) {
            DisplayState display = entry.getValue();
            display.destroy();
        }

        displays.clear();
    }
}
