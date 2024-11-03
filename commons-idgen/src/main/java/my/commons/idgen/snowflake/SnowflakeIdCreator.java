package my.commons.idgen.snowflake;

import my.commons.idgen.IdGenException;

/**
 *
 */
public class SnowflakeIdCreator {

    private static final long EPOCH = 1710209988000L;
    private static final long DEFAULT_MACHINE_ID = 0;
    private static final long MAX_MACHINE_ID = 1023;

    private static final long MACHINE_ID_BITS = 10;
    private static final long SEQUENCE_BITS = 12;
    private static final long SEQUENCE_MASK = 4095;

    private final long machineId;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public SnowflakeIdCreator() throws IdGenException {
        this(DEFAULT_MACHINE_ID);
    }

    public SnowflakeIdCreator(long machineId) throws IdGenException {
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IdGenException("machineId > maxMachineId");
        }
        this.machineId = machineId;
    }

    /**
     * @return Long
     * @throws IdGenException
     */
    public synchronized Long getId() throws IdGenException {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp < lastTimestamp) {
            throw new IdGenException("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - currentTimeStamp) + " milliseconds.");
        }
        if (lastTimestamp == currentTimeStamp) {
            sequence = (sequence + 1) % SEQUENCE_MASK;
            if (sequence == 0) {
                currentTimeStamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = currentTimeStamp;
        return ((currentTimeStamp - EPOCH) << (MACHINE_ID_BITS + SEQUENCE_BITS)) | (machineId << SEQUENCE_BITS) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) throws Exception {
        SnowflakeIdCreator idGenerator = new SnowflakeIdCreator();
        Long id = idGenerator.getId();
        System.out.println("id:" + id);
        long timestamp = (id >> (MACHINE_ID_BITS+SEQUENCE_BITS)) + EPOCH;
        long machineId = (id & ((1L << MACHINE_ID_BITS) - 1) << SEQUENCE_BITS) >> SEQUENCE_BITS;
        long sequence = id & SEQUENCE_MASK;
        System.out.println("timestamp:" + timestamp + " machineId:" + machineId + " sequence:" + sequence);
    }
}
