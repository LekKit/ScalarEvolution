package lekkit.rvvm;

public class PLIC {
    protected final RVVMMachine machine;
    protected final long plic_ctx;

    public PLIC(RVVMMachine machine) {
        if (machine.isValid()) {
            this.machine = machine;
            plic_ctx = RVVMNative.plic_init_auto(machine.machine);
        } else {
            this.machine = null;
            plic_ctx = 0;
        }
    }
}
