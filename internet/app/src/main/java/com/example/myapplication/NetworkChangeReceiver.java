package com.example.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class NetworkStatusReceiver extends BroadcastReceiver {

    private TextView connectionStatusTextView;

    public NetworkStatusReceiver(TextView connectionStatusTextView) {
        this.connectionStatusTextView = connectionStatusTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (connectionStatusTextView != null) {
            String status = NetworkStatusUtils.getNetworkStatus(context);
            connectionStatusTextView.setText(status);
        }
    }
}
