package lekkit.scev.server;

import java.util.UUID;

public interface IDisplayHandle {
    public UUID getMachineUUID();

    public boolean isValid();
}
