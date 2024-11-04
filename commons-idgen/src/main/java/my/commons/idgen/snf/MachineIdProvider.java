/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public interface MachineIdProvider {
    long getMachineId() throws RuntimeException;
}
