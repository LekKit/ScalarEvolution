package lekkit.scev.server;

import java.util.UUID;
import java.util.HashMap;

public class MachineManager {
    private static HashMap<UUID, MachineState> machines = new HashMap<UUID, MachineState>();

    public static MachineState createMachineState(UUID machine_uuid) {
        MachineState machine_state = getMachineState(machine_uuid);
        if (machine_state == null) {
            machine_state = new MachineState(machine_uuid);
            machines.put(machine_uuid, machine_state);
        }
        return machine_state;
    }

    public static MachineState getMachineState(UUID machine_uuid) {
        return machines.get(machine_uuid);
    }

    public static void removeMachineState(UUID machine_uuid) {
        MachineState machine_state = getMachineState(machine_uuid);
        if (machine_state != null) {
            machine_state.destroy();
            machines.remove(machine_uuid);
        }
    }

    public static void destroyMachineState(UUID machine_uuid) {
        removeMachineState(machine_uuid);

        // TODO: Delete machine snapshot
    }

    public static MachineState tryResumeMachineState(UUID machine_uuid) {
        // TODO: Resume machine if snapshot present
        return null;
    }
}
