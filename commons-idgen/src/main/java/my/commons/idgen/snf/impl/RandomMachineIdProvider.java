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

    public static final RandomMachineIdProvider INSTANCE = new RandomMachineIdProvider();

    private final Random random = new Random();

    @Override
    public long getMachineId(long min, long max) {
        return random.nextLong(max - min) + min;
    }
}
