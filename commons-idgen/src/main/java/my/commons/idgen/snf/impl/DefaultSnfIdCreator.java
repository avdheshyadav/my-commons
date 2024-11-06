package my.commons.idgen.snf.impl;

import my.commons.idgen.IdGenException;
import my.commons.idgen.snf.SnfIdConfig;
import my.commons.idgen.snf.SnfIdCreator;

import java.util.concurrent.atomic.AtomicLong;

public class DefaultSnfIdCreator implements SnfIdCreator {

    private final SnfIdConfig snfIdConfig;
    private final long sequenceMask;

    private long lastTimestamp = -1L;
    private final AtomicLong sequence = new AtomicLong(0L);

    public DefaultSnfIdCreator(SnfIdConfig snfIdConfig) {
        this.snfIdConfig = snfIdConfig;
        this.sequenceMask = snfIdConfig.getSequenceMask();
    }

    /**
     * @return Long
     * @throws IdGenException IdGenException
     */
    public synchronized Long createId() throws IdGenException {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp < lastTimestamp) {
            throw new IdGenException("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - currentTimeStamp) + " milliseconds.");
        }
        if (lastTimestamp == currentTimeStamp) {
            long seq = sequence.incrementAndGet() & sequenceMask;
            if (seq == 0) {
                currentTimeStamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence.set(0L);
        }
        lastTimestamp = currentTimeStamp;
        return ((currentTimeStamp - snfIdConfig.getEpoch()) << (snfIdConfig.getMachineIdBits() + snfIdConfig.getSequenceBits())) | (snfIdConfig.getMachineId() << snfIdConfig.getSequenceBits()) | sequence.get();
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
