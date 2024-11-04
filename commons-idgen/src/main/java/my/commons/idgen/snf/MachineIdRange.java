/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import lombok.RequiredArgsConstructor;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
@RequiredArgsConstructor
public class MachineIdRange {
    public static final MachineIdRange DEFAULT = new MachineIdRange(0, 1023);
    public final long min;
    public final long max;
}
