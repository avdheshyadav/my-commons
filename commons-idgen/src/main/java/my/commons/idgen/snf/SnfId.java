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
public class SnfId implements Serializable {
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
    public String toString() {
        return "SnfId{" +
                "timestamp=" + timestamp +
                ", machineId=" + machineId +
                ", sequence=" + sequence +
                '}';
    }
}
