/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package lekkit.rvvm;

public class NVMeDrive extends PCIDevice {
    public NVMeDrive(RVVMMachine machine, String imagePath, boolean rw) {
        if (machine.isValid()) {
            this.machine = machine;
            this.pci_dev = RVVMNative.nvme_init_auto(machine.machine, imagePath, rw);
        }
    }
}
