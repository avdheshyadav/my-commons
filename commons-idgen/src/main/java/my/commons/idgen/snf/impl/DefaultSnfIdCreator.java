package my.commons.idgen.snf.impl;

import my.commons.idgen.IdGenException;
import my.commons.idgen.snf.SnfIdConfig;
import my.commons.idgen.snf.SnfIdCreator;

import java.util.concurrent.atomic.AtomicLong;

public class DefaultSnfIdCreator implements SnfIdCreator {
    private final long epoch;
    private final long machineIdBits;
    private final long machineId;
    private final long sequenceBits;
    private final long sequenceMask;
    private final long timeStampLeftShift;

    private final AtomicLong lastTimestamp = new AtomicLong(-1L);
    private final AtomicLong sequence = new AtomicLong(0L);

    public DefaultSnfIdCreator(SnfIdConfig snfIdConfig) {
        this.epoch = snfIdConfig.getEpoch();
        this.machineIdBits = snfIdConfig.getMachineIdBits();
        this.machineId = snfIdConfig.getMachineId();
        this.sequenceBits = snfIdConfig.getSequenceBits();
        this.sequenceMask = snfIdConfig.getSequenceMask();
        this.timeStampLeftShift = machineIdBits + sequenceBits;
    }

    /**
     * @return Long
     * @throws IdGenException IdGenException
     */
    public synchronized Long createId() throws IdGenException {
        long currentTimeStamp = System.currentTimeMillis();
        long lastTs = lastTimestamp.get();
        if (currentTimeStamp < lastTs) {
            throw new IdGenException("Clock moved backwards.Refusing to generate id");
        }
        if (currentTimeStamp == lastTs) {
            long seq = sequence.incrementAndGet() & sequenceMask;
            if (seq == 0) {
                sequence.set(0L);
                currentTimeStamp = tilNextMillis(lastTs);
            }
        } else {
            sequence.set(0L);
        }
        lastTimestamp.set(currentTimeStamp);
        return ((currentTimeStamp - epoch) << timeStampLeftShift) | (machineId << sequenceBits) | sequence.get();
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
