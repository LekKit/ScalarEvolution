package lekkit.scev.server;

import java.util.UUID;
import lekkit.scev.items.ItemMotherboard;
import net.minecraft.inventory.IInventory;

public interface IMachineHandle extends IInventory {

    public int getComputerCaseSize();

    public ItemMotherboard getItemMotherboard();

    public UUID getMachineUUID();

    public boolean isValid();

    public void powerOn();

    public void powerOff();

    public void power();

    public void reset();

    public boolean isPowered();
}
