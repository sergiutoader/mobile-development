package com.example.contactsmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final private static int ANOTHER_ACTIVITY_REQUEST_CODE = 2017;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent phoneDialIntent = getIntent();
        if (phoneDialIntent != null) {
            Bundle data = phoneDialIntent.getExtras();
            String phoneNumber = data.get("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY").toString();

            EditText phoneTextNumber = findViewById(R.id.numberTextField);
            phoneTextNumber.setText(phoneNumber);
        }


        Button showHideButton = (Button) findViewById(R.id.showHideFieldsButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        showHideButton.setOnClickListener(view -> {
            Button button = (Button) view;
            View layout = findViewById(R.id.secondLayout);

            int visibility = layout.getVisibility();
            if (visibility == View.VISIBLE) {
                layout.setVisibility(View.INVISIBLE);
                button.setText(R.string.showAdditionalFields);
            } else {
                layout.setVisibility(View.VISIBLE);
                button.setText(R.string.hideAdditionalFields);
            }
        });

        saveButton.setOnClickListener(view -> {
            EditText nameTextField = findViewById(R.id.nameTextField);
            EditText phoneTextField = findViewById(R.id.numberTextField);
            EditText emailTextField = findViewById(R.id.emailTextField);
            EditText addressTextField = findViewById(R.id.addressTextField);
            EditText jobTitleTextField = findViewById(R.id.jobTitleTextField);
            EditText companyTextField = findViewById(R.id.companyTextField);
            EditText websiteTextField = findViewById(R.id.websiteTextField);
            EditText imTextField = findViewById(R.id.imTextField);

            String name = nameTextField.getText() == null ? null : nameTextField.getText().toString();
            String phone = phoneTextField.getText() == null ? null : phoneTextField.getText().toString();
            String email = emailTextField.getText() == null ? null : emailTextField.getText().toString();
            String address = addressTextField.getText() == null ? null : addressTextField.getText().toString();
            String jobTitle = jobTitleTextField.getText() == null ? null : jobTitleTextField.getText().toString();
            String company = companyTextField.getText() == null ? null : companyTextField.getText().toString();
            String website = websiteTextField.getText() == null ? null : websiteTextField.getText().toString();
            String im = imTextField.getText() == null ? null : imTextField.getText().toString();

            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            if (name != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            }
            if (phone != null) {
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
            }
            if (email != null) {
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
            }
            if (address != null) {
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
            }
            if (jobTitle != null) {
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
            }
            if (company != null) {
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
            }
            ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
            if (website != null) {
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                contactData.add(websiteRow);
            }
            if (im != null) {
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                contactData.add(imRow);
            }
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
            //startActivity(intent);
            startActivityForResult(intent, 1);
        });

        cancelButton.setOnClickListener(view -> {
            setResult(Activity.RESULT_CANCELED, new Intent());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}