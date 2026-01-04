package com.example.dialogs;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DialogsActivity extends AppCompatActivity implements OnUserClickListener {

    private View mainLayout;
    private Button alertDialogButton;
    private Button progressDialogButton;
    private Button datePickerButton;
    private Button timePickerButton;
    private Button snackbarButton;
    private Button customDialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        mainLayout = findViewById(R.id.main_layout);
        alertDialogButton = findViewById(R.id.btn_alert_dialog);
        progressDialogButton = findViewById(R.id.btn_progress_dialog);
        datePickerButton = findViewById(R.id.btn_date_picker);
        timePickerButton = findViewById(R.id.btn_time_picker);
        snackbarButton = findViewById(R.id.btn_snackbar);
        customDialogButton = findViewById(R.id.btn_custom_dialog);

        setupListeners();
    }

    private void setupListeners() {
        alertDialogButton.setOnClickListener(v -> showAlertDialog());
        progressDialogButton.setOnClickListener(v -> showProgressDialog());
        datePickerButton.setOnClickListener(v -> showDatePickerDialog());
        timePickerButton.setOnClickListener(v -> showTimePickerDialog());
        snackbarButton.setOnClickListener(v -> showSnackbar());
        customDialogButton.setOnClickListener(v -> showCustomDialog());
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("This is a basic alert dialog.")
                .setPositiveButton("OK", (dialog, which) ->
                        Toast.makeText(this, "OK clicked", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Cancel", (dialog, which) ->
                        Toast.makeText(this, "Cancel clicked", Toast.LENGTH_SHORT).show())
                .show();
    }

    private void showProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading, please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(progressDialog::dismiss, 3000);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    Toast.makeText(this, "Selected date: " + date, Toast.LENGTH_SHORT).show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (TimePicker view, int hourOfDay, int minute) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minute);
                    Toast.makeText(this, "Selected time: " + time, Toast.LENGTH_SHORT).show();
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void showSnackbar() {
        Snackbar.make(mainLayout, "This is a Snackbar message", Snackbar.LENGTH_LONG)
                .setAction("Undo", v ->
                        Toast.makeText(this, "Undo clicked", Toast.LENGTH_SHORT).show())
                .show();
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_custom_users, null, false);

        RecyclerView rvVerticalUsers = dialogView.findViewById(R.id.rv_vertical_users);
        rvVerticalUsers.setLayoutManager(new LinearLayoutManager(this));

        List<UserSection> sections = createDummyUserSections();
        UserSectionAdapter adapter = new UserSectionAdapter(this, sections, this);
        rvVerticalUsers.setAdapter(adapter);

        builder.setView(dialogView);
        builder.setTitle("Users");
        builder.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private List<UserSection> createDummyUserSections() {
        List<UserSection> sections = new ArrayList<>();

        List<UserModel> groupA = new ArrayList<>();
        groupA.add(new UserModel(android.R.drawable.sym_def_app_icon, "Alice", 25));
        groupA.add(new UserModel(android.R.drawable.sym_def_app_icon, "Bob", 28));
        groupA.add(new UserModel(android.R.drawable.sym_def_app_icon, "Charlie", 30));

        List<UserModel> groupB = new ArrayList<>();
        groupB.add(new UserModel(android.R.drawable.sym_def_app_icon, "David", 22));
        groupB.add(new UserModel(android.R.drawable.sym_def_app_icon, "Emma", 26));
        groupB.add(new UserModel(android.R.drawable.sym_def_app_icon, "Frank", 29));

        List<UserModel> groupC = new ArrayList<>();
        groupC.add(new UserModel(android.R.drawable.sym_def_app_icon, "Grace", 24));
        groupC.add(new UserModel(android.R.drawable.sym_def_app_icon, "Henry", 32));
        groupC.add(new UserModel(android.R.drawable.sym_def_app_icon, "Isabella", 27));

        sections.add(new UserSection("Group A", groupA));
        sections.add(new UserSection("Group B", groupB));
        sections.add(new UserSection("Group C", groupC));

        return sections;
    }

    @Override
    public void onUserClick(UserModel user) {
        Toast.makeText(this, "Clicked on " + user.getName(), Toast.LENGTH_SHORT).show();
    }
}