package io.github.ljwlgl.util;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zqgan
 * @since 2018/9/3
 * 获取系统IP
 **/

public class NetworkUtil {

    private static InetAddress m_local;

    public static InetAddress getLocalInetAddress() {
        if (m_local == null) {
            load();
        }
        return m_local;
    }

    public static String getLocalHostAddress() {
        if (m_local == null) {
            load();
        }
        return m_local.getHostAddress();
    }

    public static String getLocalHostName() {
        if (m_local == null) {
            load();
        }
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return m_local.getHostName();
        }
    }

    private static InetAddress findValidateIp(List<Address> addresses) {
        InetAddress local = null;
        int size = addresses.size();
        int maxWeight = -1;

        for (int i = 0; i < size; i++) {
            Address address = addresses.get(i);
            if (address.isInet4Address()) {
                int weight = 0;

                if (address.isSiteLocalAddress()) {
                    weight += 8;
                }

                if (address.isLinkLocalAddress()) {
                    weight += 4;
                }

                if (address.isLoopbackAddress()) {
                    weight += 2;
                }

                if (address.hasHostName()) {
                    weight += 1;
                }

                if (weight > maxWeight) {
                    maxWeight = weight;
                    local = address.getAddress();
                }
            }
        }

        return local;
    }

    private static String getProperty(String name) {
        String value = null;

        value = System.getProperty(name);

        if (value == null) {
            value = System.getenv(name);
        }

        return value;
    }

    private static void load() {
        String ip = getProperty("host.ip");

        if (ip != null) {
            try {
                m_local = InetAddress.getByName(ip);
                return;
            } catch (Exception e) {
                System.err.println(e);
                // ignore
            }
        }

        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            List<Address> addresses = new ArrayList<Address>();
            InetAddress local = null;

            try {
                for (NetworkInterface ni : nis) {
                    if (ni.isUp() && !ni.isLoopback()) {
                        List<InetAddress> list = Collections.list(ni.getInetAddresses());

                        for (InetAddress address : list) {
                            addresses.add(new Address(address, ni));
                        }
                    }
                }
                local = findValidateIp(addresses);
            } catch (Exception e) {
                // ignore
            }
            m_local = local;
        } catch (SocketException e) {
            // ignore it
        }
    }

    static class Address {
        private InetAddress m_address;

        private boolean m_loopback;

        public Address(InetAddress address, NetworkInterface ni) {
            m_address = address;

            try {
                if (ni != null && ni.isLoopback()) {
                    m_loopback = true;
                }
            } catch (SocketException e) {
                // ignore it
            }
        }

        public InetAddress getAddress() {
            return m_address;
        }

        public boolean hasHostName() {
            return !m_address.getHostName().equals(m_address.getHostAddress());
        }

        public boolean isLinkLocalAddress() {
            return !m_loopback && m_address.isLinkLocalAddress();
        }

        public boolean isLoopbackAddress() {
            return m_loopback || m_address.isLoopbackAddress();
        }

        public boolean isSiteLocalAddress() {
            return !m_loopback && m_address.isSiteLocalAddress();
        }

        public boolean isInet4Address(){
            return m_address instanceof Inet4Address;
        }
    }

}
