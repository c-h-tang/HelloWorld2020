package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);

        Button timeButton = (Button) findViewById(R.id.button);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    public void sendOnChannel1(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_reminder)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();

        notificationManager.notify(1, notification);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.TextView);
        textView.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance().format(c.getTime());
        String[] date = currentDate.split(" ");
        switch (date[0]) {
            case "Jan":
                date[0] = "01";
                break;
            case "Feb":
                date[0] = "02";
                break;
            case "Mar":
                date[0] = "03";
                break;
            case "Apr":
                date[0] = "04";
                break;
            case "May":
                date[0] = "05";
                break;
            case "Jun":
                date[0] = "06";
                break;
            case "Jul":
                date[0] = "07";
                break;
            case "Aug":
                date[0] = "08";
                break;
            case "Sep":
                date[0] = "09";
                break;
            case "Oct":
                date[0] = "10";
                break;
            case "Nov":
                date[0] = "11";
                break;
            case "Dec":
                date[0] = "12";
                break;
        }

        currentDate = date[0] + "/";

        if (date[1].substring(0, date[1].indexOf(",")).length() == 1) {
            currentDate = currentDate + "0" + date[1].substring(0, date[1].indexOf(",")) + "/" +
                    date[2];
        } else {
            currentDate = currentDate  + date[1].substring(0, date[1].indexOf(",")) + "/" + date[2];
        }

        TextView textView = (TextView) findViewById(R.id.DateView);
        textView.setText(currentDate);
    }
}