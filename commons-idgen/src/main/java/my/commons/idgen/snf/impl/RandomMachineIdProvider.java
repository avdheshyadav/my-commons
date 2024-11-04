/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf.impl;

import my.commons.idgen.snf.MachineIdProvider;
import my.commons.idgen.snf.MachineIdRange;

import java.util.Random;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class RandomMachineIdProvider implements MachineIdProvider {

    public static final RandomMachineIdProvider INSTANCE = new RandomMachineIdProvider(MachineIdRange.DEFAULT);

    private final Random random = new Random();
    private final long min ;
    private final long max;


    /**
     *
     * @param machineIdRange MachineIdRange
     */
    public RandomMachineIdProvider(MachineIdRange machineIdRange) {
        this.min = machineIdRange.min;
        this.max = machineIdRange.max;
    }

    @Override
    public long getMachineId() {
        return random.nextLong(max - min) + min;
    }
}
