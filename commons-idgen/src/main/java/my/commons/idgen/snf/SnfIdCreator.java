package my.commons.idgen.snf;

import my.commons.idgen.IdGenException;

class SnfIdCreator implements IdCreator {

    private final SnfIdConfig snfIdConfig;
    private final long machineId;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    SnfIdCreator(SnfIdConfig snfIdConfig) {
        this.snfIdConfig = snfIdConfig;
        this.machineId = snfIdConfig.getMachineIdProvider().getMachineId();
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
        return ((currentTimeStamp - snfIdConfig.getEpoch()) << (snfIdConfig.getMachineIdBits() + snfIdConfig.getSequenceBits())) | (machineId << snfIdConfig.getSequenceBits()) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
