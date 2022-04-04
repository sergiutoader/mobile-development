package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Random;

public class PracticalTest01Service extends Service {
    private Random random;
    public PracticalTest01Service() {
        random = new Random();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int leftValue = intent.getIntExtra(MainActivity.LEFT_FIELD_CONTENT_KEY, 0);
        int rightValue = intent.getIntExtra(MainActivity.RIGHT_FIELD_CONTENT_KEY, 0);

        Thread dedicatedThread = new Thread(() -> {
            double arithmetic = (1.0 * leftValue + 1.0 * rightValue) / 2;
            double geometric = Math.sqrt(leftValue * rightValue);

            while(true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(MainActivity.LOG_TAG, "Sent info...");

                switch (random.nextInt() % 3) {
                    case 0:
                        Date date = new Date();
                        String dateStr = date.toString();

                        Intent i1 = new Intent();
                        i1.setAction(MainActivity.ACTION_TIME_AND_DATE);
                        i1.putExtra(MainActivity.ACTION_TIME_AND_DATE, dateStr);
                        sendBroadcast(i1);
                        break;
                    case 1:
                        Intent i2 = new Intent();
                        i2.setAction(MainActivity.ACTION_ARITHMETIC);
                        i2.putExtra(MainActivity.ACTION_ARITHMETIC, arithmetic);
                        sendBroadcast(i2);
                        break;
                    case 2:
                        Intent i3 = new Intent();
                        i3.setAction(MainActivity.ACTION_GEOMETRIC);
                        i3.putExtra(MainActivity.ACTION_GEOMETRIC, geometric);
                        sendBroadcast(i3);
                        break;
                }
            }
        });
        dedicatedThread.start();

        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}