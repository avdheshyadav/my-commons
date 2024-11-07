package my.commons.idgen.snf.impl;

import my.commons.idgen.snf.SnfId;
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
        this.timeStampLeftShift = snfIdConfig.getMachineIdBits() + sequenceBits;
    }

    /**
     * @return long
     */
    public synchronized long createId() {
        long currentTimeStamp = System.currentTimeMillis();
        long lastTs = lastTimestamp.get();
        if (currentTimeStamp < lastTs) {
            throw new IllegalStateException("Clock moved backwards.Refusing to generate id");
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

    @Override
    public SnfId getSnfId(long id) {
        long timestamp = (id >> (machineIdBits + sequenceBits)) + epoch;
        long machineId = (id & ((1L << machineIdBits) - 1) << sequenceBits) >> sequenceBits;
        long sequence = id & sequenceMask;
        return new SnfId(timestamp, machineId, sequence);
    }

    @Override
    public long getId(SnfId snfId) {
        return ((snfId.getTimestamp() - epoch) << timeStampLeftShift) | (snfId.getMachineId() << sequenceBits) | snfId.getSequence();
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
