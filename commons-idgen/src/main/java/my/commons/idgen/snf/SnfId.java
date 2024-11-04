/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
@Getter
public class SnfId implements Serializable, Comparable<SnfId> {
    @Serial
    private static final long serialVersionUID = 1L;

    private final long timestamp;
    private final long machineId;
    private final long sequence;

    public SnfId(long timestamp, long machineId, long sequence) {
        this.timestamp = timestamp;
        this.machineId = machineId;
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnfId snfId = (SnfId) o;
        return timestamp == snfId.timestamp && machineId == snfId.machineId && sequence == snfId.sequence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, machineId, sequence);
    }

    @Override
    public int compareTo(SnfId o) {
        if (this.timestamp > o.timestamp || (timestamp == o.timestamp && this.machineId == o.machineId && this.sequence > o.sequence)) {
            return 1;
        } else if (this.timestamp < o.timestamp) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "SnfId{" +
                "timestamp=" + timestamp +
                ", machineId=" + machineId +
                ", sequence=" + sequence +
                '}';
    }
}
