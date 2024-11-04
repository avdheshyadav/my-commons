/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf.impl;

import lombok.Getter;
import lombok.Setter;
import my.commons.idgen.snf.MachineIdProvider;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class MachineId {
    public static final long DEFAULT_MACHINE_ID_BITS = 10;
    public static final MachineId DEFAULT_MACHINE_ID = new MachineId(DEFAULT_MACHINE_ID_BITS);

    @Getter
    private final long machineIdBits;
    private final long maxMachineId;
    @Setter
    private MachineIdProvider machineIdProvider = RandomMachineIdProvider.INSTANCE;

    public MachineId(long machineIdBits) {
        this.machineIdBits = machineIdBits;
        this.maxMachineId =  (1L << machineIdBits) - 1;
    }

    /**
     *
     * @param machineIdBits long
     * @param machineIdProvider MachineIdProvider
     */
    public MachineId(long machineIdBits, MachineIdProvider machineIdProvider) {
        this(machineIdBits);
        this.machineIdProvider = machineIdProvider;
    }

    /**
     * @return long
     */
    public long getMachineId() {
        return machineIdProvider.getMachineId(0, maxMachineId);
    }
}
