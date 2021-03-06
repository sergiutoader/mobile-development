package ro.pub.cs.systems.eim.lab05.startedserviceactivity.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import ro.pub.cs.systems.eim.lab05.startedserviceactivity.R;
import ro.pub.cs.systems.eim.lab05.startedserviceactivity.general.Constants;

public class StartedServiceActivity extends AppCompatActivity {

    private TextView messageTextView;
    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver;
    private IntentFilter startedServiceIntentFilter;
    private Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);

        messageTextView = (TextView)findViewById(R.id.message_text_view);

        // TODO: exercise 6 - start the service
        service = new Intent();
        service.setComponent(new ComponentName("ro.pub.cs.systems.eim.lab05.startedservice",
                "ro.pub.cs.systems.eim.lab05.startedservice.service.StartedService"));
        startService(service);

        // TODO: exercise 8a - create an instance of the StartedServiceBroadcastReceiver broadcast receiver
        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver(messageTextView);
        // TODO: exercise 8b - create an instance of an IntentFilter
        // with all available actions contained within the broadcast intents sent by the service
        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction(Constants.ACTION_INTEGER);
        startedServiceIntentFilter.addAction(Constants.ACTION_STRING);
        startedServiceIntentFilter.addAction(Constants.ACTION_ARRAY_LIST);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: exercise 8c - register the broadcast receiver with the corresponding intent filter
        registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        // TODO: exercise 8c - unregister the broadcast receiver
        unregisterReceiver(startedServiceBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO: exercise 8d - stop the service
        stopService(service);
        super.onDestroy();
    }

    // TODO: exercise 9 - implement the onNewIntent callback method
    // get the message from the extra field of the intent
    // and display it in the messageTextView

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case (Constants.ACTION_INTEGER):
                int intData = intent.getIntExtra(Constants.DATA, 0);
                messageTextView.setText(
                        messageTextView.getText() + "\n" + intData);
                break;
            case (Constants.ACTION_STRING):
                String stringData = intent.getStringExtra(Constants.DATA);
                messageTextView.setText(
                        messageTextView.getText() + "\n" + stringData);
                break;
            case (Constants.ACTION_ARRAY_LIST):
                ArrayList<String> arrayListData = intent.getStringArrayListExtra(Constants.DATA);
                messageTextView.setText(
                        messageTextView.getText() + "\n" + arrayListData);
                break;
        };
        super.onNewIntent(intent);
    }
}
