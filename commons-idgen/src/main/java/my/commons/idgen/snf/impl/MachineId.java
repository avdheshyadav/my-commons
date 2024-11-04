/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf.impl;

import lombok.Getter;
import my.commons.idgen.snf.MachineIdProvider;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class MachineId {
    public static final MachineId DEFAULT_MACHINE_ID = new MachineId(12, RandomMachineIdProvider.INSTANCE);

    @Getter
    private final long machineIdBits;
    private final MachineIdProvider machineIdProvider;

    /**
     *
     * @param machineIdBits long
     * @param machineIdProvider MachineIdProvider
     */
    public MachineId(long machineIdBits, MachineIdProvider machineIdProvider) {
        this.machineIdBits = machineIdBits;
        this.machineIdProvider = machineIdProvider;
    }

    /**
     * @return long
     */
    public long getMachineId() {
        return machineIdProvider.getMachineId();
    }
}
