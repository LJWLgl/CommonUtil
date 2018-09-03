package io.github.ljwlgl.system;

import com.google.common.base.Strings;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author zqgan
 * @since 2018/9/3
 **/

public class SystemUtil {

    private static String localHostAddress;

    /**
     * 获取本机IP，根据网卡取本机配置的IP
     */
    public static String getLocalHostAddress() {
        if ( Strings.isNullOrEmpty(localHostAddress)) {
            Enumeration netInterfaces= null;
            try {
                netInterfaces = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException e) {
                return null;
            }
            while(netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
                if (ni == null) {
                    continue;
                }
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    InetAddress ip = ias.nextElement();
                    if (ip instanceof Inet4Address && ! ip.isLoopbackAddress()
                            && ! ip.getHostAddress().contains(":")) {
                        localHostAddress = ip.getHostAddress();
                        break;
                    }
                }
            }

        }
        return localHostAddress;
    }

}
