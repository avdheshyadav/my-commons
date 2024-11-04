/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import lombok.Getter;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
@Getter
public class SnfIdConfig {

    private static final long DEFAULT_EPOCH = 1710209988000L;
    private static final long DEFAULT_MACHINE_ID_BITS = 10;
    private static final long DEFAULT_SEQUENCE_BITS = 12;

    public static final SnfIdConfig DEFAULT_CONFIG = new SnfIdConfig(DEFAULT_EPOCH,
            NetworkInterfaceMachineIdProvider.INSTANCE, DEFAULT_MACHINE_ID_BITS, DEFAULT_SEQUENCE_BITS);

    private final long epoch;
    private final MachineIdProvider machineIdProvider;
    private final long machineIdBits;
    private final long sequenceBits;
    private final long sequenceMask;

    public SnfIdConfig(long epoch, MachineIdProvider machineIdProvider, long machineIdBits, long sequenceBits) {
        this.epoch = epoch;
        this.machineIdProvider = machineIdProvider;
        this.machineIdBits = machineIdBits;
        this.sequenceBits = sequenceBits;
        this.sequenceMask = (1L << sequenceBits) - 1;
    }

}
