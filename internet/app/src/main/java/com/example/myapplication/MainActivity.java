package com.example.network;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NetworkActivity extends AppCompatActivity {

    private CheckBox checkBoxRealTime;
    private Button buttonStatus;
    private TextView textViewConnectionStatus;
    private NetworkStatusReceiver networkStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupListeners();
        showInitialStatus();
    }

    private void initializeViews() {
        checkBoxRealTime = findViewById(R.id.checkboxRealTime);
        buttonStatus = findViewById(R.id.buttonCheckStatus);
        textViewConnectionStatus = findViewById(R.id.textViewStatus);
    }

    private void setupListeners() {
        checkBoxRealTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                activateRealTimeMonitoring();
                textViewConnectionStatus.setText("Real-time monitoring is active. The status will update automatically.");
            } else {
                deactivateRealTimeMonitoring();
                textViewConnectionStatus.setText("Real-time monitoring is off. Check manually by pressing the button.");
            }
        });

        buttonStatus.setOnClickListener(v -> {
            if (checkBoxRealTime.isChecked()) {
                // Show dialog when real-time monitoring is active
                displayStatusDialog();
            } else {
                // Update TextView when real-time monitoring is inactive
                refreshStatus();
            }
        });
    }

    private void showInitialStatus() {
        String status = NetworkStatusUtils.getNetworkStatus(this);
        textViewConnectionStatus.setText(status);
    }

    private void refreshStatus() {
        String status = NetworkStatusUtils.getNetworkStatus(this);
        textViewConnectionStatus.setText(status);
    }

    private void displayStatusDialog() {
        String status = NetworkStatusUtils.getNetworkStatus(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Network Status");
        builder.setMessage(status);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void activateRealTimeMonitoring() {
        networkStatusReceiver = new NetworkStatusReceiver(textViewConnectionStatus);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(networkStatusReceiver, filter);
    }

    private void deactivateRealTimeMonitoring() {
        if (networkStatusReceiver != null) {
            unregisterReceiver(networkStatusReceiver);
            networkStatusReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deactivateRealTimeMonitoring();
    }
}