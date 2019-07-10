package com.example.android_pda_system.uConfig.Device_Info;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/*******************************************************************************
 *    Description  : PDA Android divice ip
 *    Usage        :  pda가 현재 연결중인 ip주소를 가지고 오는 기능.
 *
 *    사용 xml     :
 *    *****************************************************************************
 *                                MODIFICATION LOG
 *
 *         DATE            AUTHORS                          DESCRiPTiON
 *     -----------    -----------------                 -------------------
 *     2019.06.03		JH_KIM                            CREATE
 *
 *******************************************************************************/

public class Device_IpAddress_Get {
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //todo  네트워크에는 항상 Localhost 즉, 루프백(LoopBack)주소가 있으며, 우리가 원하는 것이 아님.
                    // IP는 IPv6와 IPv4가 있습니다.
                    // IPv6의 형태 : fe80::64b9::c8dd:7003
                    // IPv4의 형태 : 123.234.123.123
                    // 어떻게 나오는지는 찍어보세요.
                    if (!inetAddress.isSiteLocalAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isLoopbackAddress()) {
                        Log.i("IPAddress", intf.getDisplayName() + "(loopback) | " + inetAddress.getHostAddress());
                    } else {
                        Log.i("IPAddress", intf.getDisplayName() + " | " + inetAddress.getHostAddress());
                    }

                    //todo  루프백이 아니고, IPv4가 맞다면 리턴~~~
                    if (!inetAddress.isLoopbackAddress()) {
                        if (inetAddress instanceof java.net.Inet4Address)
                            return inetAddress.getHostAddress();
                    }

                }
            }
        } catch (SocketException ex) {
            Log.e("ERROR", ex.toString());
        }

        return null;
    }
}
