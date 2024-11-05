/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf.impl;

import my.commons.idgen.snf.SnfIdConfig;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class DefaultSnfIdConfigImpl implements SnfIdConfig {

    public static final long DEF_EPOCH = 1710209988000L;
    public static final long DEF_SEQUENCE_BITS = 12;

    public static final DefaultSnfIdConfigImpl DEFAULT_CONFIG = new DefaultSnfIdConfigImpl(DEF_EPOCH, MachineId.DEF_MACHINE_ID, DEF_SEQUENCE_BITS);

    private final long epoch;
    private final long machineId;
    private final long machineIdBits;
    private final long sequenceBits;
    private final long sequenceMask;


    public DefaultSnfIdConfigImpl(long epoch, MachineId machineId, long sequenceBits) {
        this.epoch = epoch;
        this.machineId = machineId.getMachineId();
        this.machineIdBits = machineId.getMachineIdBits();
        this.sequenceBits = sequenceBits;
        this.sequenceMask = (1L << sequenceBits) - 1;
    }

    @Override
    public long getEpoch() {
        return epoch;
    }

    @Override
    public long getMachineIdBits() {
        return machineIdBits;
    }

    @Override
    public long getMachineId() {
        return machineId;
    }

    @Override
    public long getSequenceBits() {
        return sequenceBits;
    }

    @Override
    public long getSequenceMask() {
        return sequenceMask;
    }
}
