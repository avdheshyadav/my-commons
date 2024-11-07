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
    private static SnfIdConfig snfIdConfig;
    private static SnfIdCreator ID_CREATOR;

    public static Long getId() throws IdGenException {
        if (ID_CREATOR == null) {
            try {
                init(DefaultSnfIdConfigImpl.DEFAULT_CONFIG);
            }catch (IdGenException id){
                System.out.println("Id Creator already initialized");
            }
        }
        return ID_CREATOR.createId();
    }

    public static Long getId(SnfId snfId) {
        return ((snfId.getTimestamp() - snfIdConfig.getEpoch()) << (snfIdConfig.getMachineIdBits() + snfIdConfig.getSequenceBits())) | (snfId.getMachineId() << snfIdConfig.getSequenceBits()) | snfId.getSequence();
    }

    public static SnfId getSnfId(Long id) {
        long timestamp = (id >> (snfIdConfig.getMachineIdBits() + snfIdConfig.getSequenceBits())) + snfIdConfig.getEpoch();
        long machineId = (id & ((1L << snfIdConfig.getMachineIdBits()) - 1) << snfIdConfig.getSequenceBits()) >> snfIdConfig.getSequenceBits();
        long sequence = id & snfIdConfig.getSequenceMask();
        return new SnfId(timestamp, machineId, sequence);
    }

    public static void registerIdCreator(SnfIdConfig snfIdConfig) throws Exception {
        if (ID_CREATOR == null) {
            init(snfIdConfig);
        }else{
            System.out.println("Id Creator already initialized.");
        }
    }

    private synchronized static void init(SnfIdConfig snfIdConfig) throws IdGenException {
        if (ID_CREATOR == null) {
            SnfIdGen.snfIdConfig = snfIdConfig;
            ID_CREATOR = new DefaultSnfIdCreator(snfIdConfig);
            System.out.println("Id Creator initialized By: " + Thread.currentThread().getName());
        } else {
            throw new IdGenException("IdGen already registered");
        }
    }
}
