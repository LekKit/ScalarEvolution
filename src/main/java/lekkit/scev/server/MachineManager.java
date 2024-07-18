package lekkit.scev.server;

import java.util.UUID;
import java.util.HashMap;

public class MachineManager {
    private static HashMap<UUID, MachineState> machines = new HashMap<UUID, MachineState>();

    public synchronized static MachineState createMachineState(UUID machineUUID, long mem_mb, int smp, boolean rv64) {
        MachineState state = getMachineState(machineUUID);
        if (state != null) {
            System.out.println("Machine already exists!");
            return null;
        }

        state = new MachineState(machineUUID);
        if (state.create(mem_mb, smp, rv64)) {
            machines.put(machineUUID, state);

            return state;
        }

        System.out.println("Failed to create machine!");

        return null;
    }

    public synchronized static MachineState getMachineState(UUID machineUUID) {
        return machines.get(machineUUID);
    }

    public synchronized static void removeMachineState(UUID machineUUID) {
        MachineState state = getMachineState(machineUUID);
        if (state != null) {
            state.destroy();
            machines.remove(machineUUID);
        }
    }

    public synchronized static void destroyMachineState(UUID machineUUID) {
        removeMachineState(machineUUID);

        // TODO: Delete machine snapshot
    }

    public synchronized static boolean hasMachineStateSnapshot(UUID machineUUID) {
        // TODO: Check snapshot presence for machine UUID
        return false;
    }

    /*
     * May be called from client thread since there's no other way to
     * handle pause events in Minecraft
     */

    public synchronized static void pauseAllMachines() {
        for (HashMap.Entry<UUID, MachineState> entry : machines.entrySet()) {
            MachineState state = entry.getValue();
            state.pause();
        }
    }

    public synchronized static void unpauseAllMachines() {
        for (HashMap.Entry<UUID, MachineState> entry : machines.entrySet()) {
            MachineState state = entry.getValue();
            state.unpause();
        }
    }
}
