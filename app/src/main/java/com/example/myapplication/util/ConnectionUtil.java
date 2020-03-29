package com.example.myapplication.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionUtil {
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo acttiveNetwork = cm.getActiveNetworkInfo();
        return acttiveNetwork !=null &&  acttiveNetwork.isConnectedOrConnecting();

    }
}
