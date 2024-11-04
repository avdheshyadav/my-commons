/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf.impl;

import my.commons.idgen.snf.MachineIdProvider;
import my.commons.idgen.snf.MachineIdRange;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class NetworkInterfaceMachineIdProvider implements MachineIdProvider {

    public static final NetworkInterfaceMachineIdProvider INSTANCE = new NetworkInterfaceMachineIdProvider(MachineIdRange.DEFAULT);

    private long min;
    private long max;

    /**
     * @param machineIdRange MachineIdRange
     */
    public NetworkInterfaceMachineIdProvider(MachineIdRange machineIdRange) {
        this.min = machineIdRange.min;
        this.max = machineIdRange.max;
    }

    @Override
    public long getMachineId() throws RuntimeException {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            long id;
            if (network == null) {
                id = 1;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
            }
            if (id > 0 && id <= max) {
                return id;
            } else {
                throw new RuntimeException("Invalid machine id:" + id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
