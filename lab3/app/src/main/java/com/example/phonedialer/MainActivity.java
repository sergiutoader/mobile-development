package com.example.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String numberString = "";
    private static final int MAX_NUMBER_SIZE = 12;
    private static final String DEFAULT_STRING = "Enter phone number";

    private final static int ANOTHER_ACTIVITY_REQUEST_CODE = 2017;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main_land);
        }
    }

    public void backspacePressed(View view) {
        if (numberString.length() > 0) {
            numberString = numberString.substring(0, numberString.length() - 1);
        }
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        if (numberString.length() == 0) {
            numberTextView.setText(DEFAULT_STRING);
        } else {
            numberTextView.setText(numberString);
        }
    }

    public void numberPressed(View view) {
        if (numberString.length() > MAX_NUMBER_SIZE) {
            return;
        }

        Button button = (Button) view;
        String value = button.getText().toString();
        numberString += value;

        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        numberTextView.setText(numberString);
    }

    public void closeActivity(View view) {
        numberString = "";
        finish();
    }

    public void makeCall(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + numberString));
            startActivity(intent);
        }
    }

    public void addContact(View view) {

        if (numberString.length() > 0) {
            Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
            intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", numberString);
            startActivityForResult(intent, ANOTHER_ACTIVITY_REQUEST_CODE);
        } else {
            Toast.makeText(getApplication(), "Please specify the number", Toast.LENGTH_LONG).show();
        }
    }
}