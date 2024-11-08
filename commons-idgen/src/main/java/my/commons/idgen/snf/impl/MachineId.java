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
@Getter
public class MachineId {
    public static final long DEF_MACHINE_ID_BITS = 10;
    public static final MachineId DEF_MACHINE_ID = new MachineId(DEF_MACHINE_ID_BITS,RandomMachineIdProvider.INSTANCE);

    private final long machineIdBits;
    private final long machineId;

    /**
     *
     * @param machineIdBits long
     * @param machineIdProvider MachineIdProvider
     */
    public MachineId(long machineIdBits, MachineIdProvider machineIdProvider) {
        this.machineIdBits = machineIdBits;
        long maxMachineId = (1L << machineIdBits) - 1;
        this.machineId = machineIdProvider.getMachineId(0, maxMachineId);
    }
}
