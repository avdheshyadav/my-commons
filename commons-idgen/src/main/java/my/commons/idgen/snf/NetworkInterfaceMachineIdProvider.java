/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class NetworkInterfaceMachineIdProvider implements MachineIdProvider {

    public static final NetworkInterfaceMachineIdProvider INSTANCE = new NetworkInterfaceMachineIdProvider();

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
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
