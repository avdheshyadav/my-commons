package my.commons.idgen.snf;

import my.commons.idgen.IdGenException;

class SnfIdCreator implements IdCreator {
    private final long epoch;
    private final long machineId;
    private final long machineIdBits;
    private final long sequenceBits;
    private final long sequenceMask;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    SnfIdCreator(long epoch, long machineId, long machineIdBits, long sequenceBits, long sequenceMask) {
        this.epoch = epoch;
        this.machineId = machineId;
        this.machineIdBits = machineIdBits;
        this.sequenceBits = sequenceBits;
        this.sequenceMask = sequenceMask;
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
            sequence = (sequence + 1) % sequenceMask;
            if (sequence == 0) {
                currentTimeStamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = currentTimeStamp;
        new SnfId(currentTimeStamp,machineId, sequence);
        return ((currentTimeStamp - epoch) << (machineIdBits + sequenceBits)) | (machineId << sequenceBits) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
