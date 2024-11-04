/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
@Getter
@Setter
public class SnfIdConfig {

    private static final long EPOCH = 1710209988000L;

    private static final long MACHINE_ID_BITS = 10;
    private static final long SEQUENCE_BITS = 12;
    private static final long SEQUENCE_MASK = 4095;

    public static final SnfIdConfig DEFAULT_CONFIG = new SnfIdConfig(EPOCH,
            NetworkInterfaceMachineIdProvider.INSTANCE, MACHINE_ID_BITS, SEQUENCE_BITS, SEQUENCE_MASK);

    private long epoch;
    private MachineIdProvider machineIdProvider;
    private long machineIdBits;
    private long sequenceBits;
    private long sequenceMask;

    public SnfIdConfig(long epoch, MachineIdProvider machineIdProvider, long machineIdBits, long sequenceBits, long sequenceMask) {
        this.epoch = epoch;
        this.machineIdProvider = machineIdProvider;
        this.machineIdBits = machineIdBits;
        this.sequenceBits = sequenceBits;
        this.sequenceMask = sequenceMask;
    }

}
