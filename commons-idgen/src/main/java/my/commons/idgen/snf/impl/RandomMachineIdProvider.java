/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf.impl;

import my.commons.idgen.snf.MachineIdProvider;

import java.util.Random;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class RandomMachineIdProvider implements MachineIdProvider {

    public static final RandomMachineIdProvider INSTANCE = new RandomMachineIdProvider(0, 1);

    private static final int DEFAULT_MIN = 0;
    private static final int DEFAULT_MAX = 1023;

    private int min = DEFAULT_MIN;
    private int max = DEFAULT_MAX;

    private Random random = new Random();

    public RandomMachineIdProvider(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public long getMachineId() {
        return random.nextInt(max - min) + min;
    }
}
