/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import my.commons.idgen.IdGenException;
import my.commons.idgen.snf.impl.DefaultSnfIdConfigImpl;
import my.commons.idgen.snf.impl.DefaultSnfIdCreator;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class SnfIdGen {

    private static DefaultSnfIdConfigImpl snfIdConfig;

    private static SnfIdCreator ID_CREATOR;

    /**
     * @param snfIdConfig SnfIdConfig
     * @throws Exception Exception
     */
    public synchronized static void registerIdCreator(DefaultSnfIdConfigImpl snfIdConfig) throws Exception {
        if (ID_CREATOR == null) {
            SnfIdGen.snfIdConfig = snfIdConfig;
            ID_CREATOR = new DefaultSnfIdCreator(snfIdConfig);
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
