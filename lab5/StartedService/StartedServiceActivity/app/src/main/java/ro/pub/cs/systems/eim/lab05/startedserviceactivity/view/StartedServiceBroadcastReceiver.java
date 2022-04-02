package ro.pub.cs.systems.eim.lab05.startedserviceactivity.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import java.util.ArrayList;

import ro.pub.cs.systems.eim.lab05.startedserviceactivity.general.Constants;

public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    private TextView messageTextView;

    // TODO: exercise 9 - default constructor
    public StartedServiceBroadcastReceiver() {
        messageTextView = null;
    }

    public StartedServiceBroadcastReceiver(TextView messageTextView) {
        this.messageTextView = messageTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: exercise 9 - restart the activity through an intent
        // if the messageTextView is not available
        String action = intent.getAction();

        if (messageTextView == null) {
            Intent newActivity = new Intent(context, StartedServiceActivity.class);
            switch (action) {
                case (Constants.ACTION_INTEGER):
                    int intData = intent.getIntExtra(Constants.DATA, 0);
                    newActivity.putExtra(Constants.DATA, intData);
                    break;
                case (Constants.ACTION_STRING):
                    String stringData = intent.getStringExtra(Constants.DATA);
                    newActivity.putExtra(Constants.DATA, stringData);

                    break;
                case (Constants.ACTION_ARRAY_LIST):
                    ArrayList<String> arrayListData = intent.getStringArrayListExtra(Constants.DATA);
                    newActivity.putExtra(Constants.DATA, arrayListData);
                    break;
            };
            newActivity.setAction(action);
            newActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(newActivity);
            return;
        }

        // TODO: exercise 7 - get the action and the extra information from the intent
        // and set the text on the messageTextView


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
    }
}
