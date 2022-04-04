package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    public StartedServiceBroadcastReceiver() {
        Log.d(MainActivity.LOG_TAG, "receiver initiated");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.d(MainActivity.LOG_TAG, "on receive : " + action);


        switch (action) {
            case (MainActivity.ACTION_TIME_AND_DATE):

               // Log.d(MainActivity.LOG_TAG, "date and time");
                String date = intent.getStringExtra(MainActivity.ACTION_TIME_AND_DATE);
                Toast.makeText(context, "Date: " + date, Toast.LENGTH_SHORT).show();
                break;
            case (MainActivity.ACTION_ARITHMETIC):

               // Log.d(MainActivity.LOG_TAG, "arithmetic");
                double arithmetic = intent.getDoubleExtra(MainActivity.ACTION_ARITHMETIC, 0);
                Toast.makeText(context, "Arithmetic: " + arithmetic, Toast.LENGTH_SHORT).show();
                break;
            case (MainActivity.ACTION_GEOMETRIC):

                // Log.d(MainActivity.LOG_TAG, "geometric");
                double geometric = intent.getDoubleExtra(MainActivity.ACTION_GEOMETRIC, 0);
                Toast.makeText(context, "Geometric: " + geometric, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
