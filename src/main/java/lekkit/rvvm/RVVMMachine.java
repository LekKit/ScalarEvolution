/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package lekkit.rvvm;

import java.nio.ByteBuffer;

public class RVVMMachine {
    private long machine = 0;

    public static final int RVVM_OPT_NONE         = 0x0;
    public static final int RVVM_OPT_RESET_PC     = 0x1; //!< Physical jump address at reset, defaults to 0x80000000
    public static final int RVVM_OPT_DTB_ADDR     = 0x2; //!< Pass DTB address if non-zero, omits FDT generation
    public static final int RVVM_OPT_TIME_FREQ    = 0x3; //!< Machine timer frequency, 10Mhz by default
    public static final int RVVM_OPT_HW_IMITATE   = 0x4; //!< Imitate traits or identity of physical hardware
    public static final int RVVM_OPT_MAX_CPU_CENT = 0x5; //!< Max CPU load % per guest/host CPUs
    public static final int RVVM_OPT_JIT          = 0x6; //!< Enable JIT
    public static final int RVVM_OPT_JIT_CACHE    = 0x7; //!< Amount of per-core JIT cache (In bytes)
    public static final int RVVM_OPT_JIT_HARVARD  = 0x8; //!< No dirty code tracking, explicit ifence, slower

    public static final int RVVM_OPT_MEM_BASE   = 0x80000001; //!< Physical RAM base address, defaults to 0x80000000
    public static final int RVVM_OPT_MEM_SIZE   = 0x80000002; //!< Physical RAM size
    public static final int RVVM_OPT_HART_COUNT = 0x80000003; //!< Amount of harts

    public RVVMMachine(long mem_mb, int smp, String isa) {
        if (RVVMNative.isLoaded()) {
            this.machine = RVVMNative.create_machine(mem_mb << 20, smp, isa);
        }

        if (isValid()) {
            RVVMNative.riscv_clint_init_auto(machine);
        }
    }

    public boolean isValid() {
        return machine != 0;
    }

    public long getPtr() {
        return machine;
    }

    public ByteBuffer getDmaBuffer(long addr, long size) {
        if (isValid()) {
            return RVVMNative.get_dma_buf(machine, addr, size);
        }
        return null;
    }

    public void setCmdline(String cmdline) {
        if (isValid()) {
            RVVMNative.set_cmdline(machine, cmdline);
        }
    }

    public void appendCmdline(String cmdline) {
        if (isValid()) {
            RVVMNative.append_cmdline(machine, cmdline);
        }
    }

    public long getOption(int opt) {
        if (isValid()) {
            return RVVMNative.get_opt(machine, opt);
        }
        return 0;
    }

    public void setOption(int opt, long val) {
        if (isValid()) {
            RVVMNative.set_opt(machine, opt, val);
        }
    }

    public boolean loadBootrom(String path) {
        if (isValid()) {
            return RVVMNative.load_bootrom(machine, path);
        }
        return false;
    }

    public boolean loadKernel(String path) {
        if (isValid()) {
            return RVVMNative.load_kernel(machine, path);
        }
        return false;
    }

    public boolean loadDeviceTree(String path) {
        if (isValid()) {
            return RVVMNative.load_dtb(machine, path);
        }
        return false;
    }

    public boolean dumpDeviceTree(String path) {
        if (isValid()) {
            return RVVMNative.dump_dtb(machine, path);
        }
        return false;
    }

    public boolean start() {
        if (isValid()) {
            return RVVMNative.start_machine(machine);
        }
        return false;
    }

    public boolean reset() {
        if (isValid()) {
            return RVVMNative.reset_machine(machine, true);
        }
        return false;
    }

    public boolean poweroff() {
        if (isValid()) {
            return RVVMNative.reset_machine(machine, false);
        }
        return false;
    }

    public boolean pause() {
        if (isValid()) {
            return RVVMNative.pause_machine(machine);
        }
        return false;
    }

    public boolean isPowered() {
        if (isValid()) {
            return RVVMNative.machine_powered(machine);
        }
        return false;
    }

    // Beware to drop all references to devices owned by this machine beforehand
    public synchronized void free() {
        if (isValid()) {
            RVVMNative.free_machine(machine);
            machine = 0;
        }
    }

    // Should not be relied upon
    @Override
    protected synchronized void finalize() {
        free();
    }
}
