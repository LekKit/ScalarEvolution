/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package lekkit.rvvm;

public class RTL8169 extends PCIDevice {
    public RTL8169(RVVMMachine machine) {
        super(machine);
        if (machine.isValid()) {
            long pci_bus = RVVMNative.get_pci_bus(machine.getPtr());
            if (pci_bus != 0) {
                long tap = RVVMNative.tap_user_open();
                if (tap != 0) {
                    setPCIHandle(RVVMNative.rtl8169_init(pci_bus,tap));
                }
            }
        }
    }
}
