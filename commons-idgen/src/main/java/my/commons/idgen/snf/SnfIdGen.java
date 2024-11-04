/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import my.commons.idgen.IdGenException;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class SnfIdGen {

    private static SnfIdConfig snfIdConfig;

    private static IdCreator ID_CREATOR;

    /**
     * @param snfIdConfig SnfIdConfig
     * @throws Exception Exception
     */
    public synchronized static void registerIdCreator(SnfIdConfig snfIdConfig) throws Exception {
        if (ID_CREATOR == null) {
            SnfIdGen.snfIdConfig = snfIdConfig;
            long machineId = snfIdConfig.getMachineIdProvider().getMachineId();
            registerIdCreator(snfIdConfig.getEpoch(), machineId, snfIdConfig.getMachineIdBits(), snfIdConfig.getSequenceBits(), snfIdConfig.getSequenceMask());
        } else {
            throw new IdGenException("IdGen already registered");
        }
    }

    public synchronized static void registerIdCreator(long epoch, long machineId, long machineBits, long sequenceBits, long sequenceMask) throws Exception {
        if (ID_CREATOR == null) {
            ID_CREATOR = new SnfIdCreator(epoch, machineId, machineBits, sequenceBits, sequenceMask);
        } else {
            throw new IdGenException("IdGen already registered");
        }
    }

    public static Long getId() throws IdGenException {
        return ID_CREATOR.createId();
    }

    public static Long getId(SnfId snfId) throws IdGenException {
        return ((snfId.getTimestamp() - snfIdConfig.getEpoch()) << (snfIdConfig.getMachineIdBits() + snfIdConfig.getSequenceBits())) | (snfId.getMachineId() << snfIdConfig.getSequenceBits()) | snfId.getSequence();
    }

    public static SnfId getSnfId(Long id) throws IdGenException {
        long timestamp = (id >> (snfIdConfig.getMachineIdBits() + snfIdConfig.getSequenceBits())) + snfIdConfig.getEpoch();
        long machineId = (id & ((1L << snfIdConfig.getMachineIdBits()) - 1) << snfIdConfig.getSequenceBits()) >> snfIdConfig.getSequenceBits();
        long sequence = id & snfIdConfig.getSequenceMask();
        return new SnfId(timestamp, machineId, sequence);
    }
}
