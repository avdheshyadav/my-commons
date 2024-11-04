package my.commons.idgen.snf.impl;

import my.commons.idgen.IdGenException;
import my.commons.idgen.snf.SnfIdConfig;
import my.commons.idgen.snf.SnfIdCreator;

public class DefaultSnfIdCreator implements SnfIdCreator {

    private final SnfIdConfig snfIdConfig;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public DefaultSnfIdCreator(SnfIdConfig snfIdConfig) {
        this.snfIdConfig = snfIdConfig;
    }

    /**
     * @return Long
     * @throws IdGenException IdGenException
     */
    public Long createId() throws IdGenException {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp < lastTimestamp) {
            throw new IdGenException("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - currentTimeStamp) + " milliseconds.");
        }
        if (lastTimestamp == currentTimeStamp) {
            sequence = (sequence + 1) % snfIdConfig.getSequenceMask();
            if (sequence == 0) {
                currentTimeStamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = currentTimeStamp;
        return ((currentTimeStamp - snfIdConfig.getEpoch()) << (snfIdConfig.getMachineIdBits() + snfIdConfig.getSequenceBits())) | (snfIdConfig.getMachineId() << snfIdConfig.getSequenceBits()) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
