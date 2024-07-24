package lekkit.scev.server;

import java.util.UUID;

public interface IMachineHandle {
    public UUID getMachineUUID();

    public boolean isValid();
}
