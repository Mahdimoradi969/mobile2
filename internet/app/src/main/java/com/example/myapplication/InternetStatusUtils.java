package com.example.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

public class NetworkStatusUtils {

    public static boolean isMobileDataActive(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;

            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            if (capabilities == null) return false;

            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        } else {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return networkInfo != null && networkInfo.isConnected();
        }
    }

    public static boolean isWifiActive(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;

            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            if (capabilities == null) return false;

            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return networkInfo != null && networkInfo.isConnected();
        }
    }

    public static boolean isNetworkConnected(Context context) {
        return isMobileDataActive(context) || isWifiActive(context);
    }

    public static String getNetworkStatus(Context context) {
        StringBuilder status = new StringBuilder();

        if (isNetworkConnected(context)) {
            status.append("Network: Connected\n\n");

            if (isWifiActive(context)) {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager != null) {
                    int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
                    String ipAddressString = String.format("%d.%d.%d.%d",
                            (ipAddress & 0xff),
                            (ipAddress >> 8 & 0xff),
                            (ipAddress >> 16 & 0xff),
                            (ipAddress >> 24 & 0xff));
                    status.append("Wi-Fi: Connected\n");
                    status.append("IP Address: ").append(ipAddressString).append("\n");
                }
            } else if (isMobileDataActive(context)) {
                status.append("Mobile Data: Connected\n");
            }
        } else {
            status.append("Network: Disconnected\n\n");
            status.append("Neither Wi-Fi nor Mobile Data is available.");
        }

        return status.toString();
    }
}
