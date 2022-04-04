package ro.pub.cs.systems.eim.practicaltest01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static String LEFT_FIELD_CONTENT_KEY = "left_field_content";
    public final static String RIGHT_FIELD_CONTENT_KEY = "right_field_content";
    public final static String INTENT_CONTENT_KEY = "intent_content_key";

    public final static String ACTION_TIME_AND_DATE = "time_and_date";
    public final static String ACTION_ARITHMETIC = "arithmetic";
    public final static String ACTION_GEOMETRIC = "geometric";

    public final static int THRESHOLD = 10;
    public final static String LOG_TAG = "Partial01BcastReceiver";

    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver;
    private IntentFilter startedServiceIntentFilter;
    private Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            TextView leftTextView = findViewById(R.id.score_text_view_1);
            TextView rightTextView = findViewById(R.id.score_text_view_2);

            String leftText = savedInstanceState.getString(LEFT_FIELD_CONTENT_KEY);
            String rightText = savedInstanceState.getString(RIGHT_FIELD_CONTENT_KEY);

            leftTextView.setText(leftText);
            rightTextView.setText(rightText);
        }

        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver();
        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction(MainActivity.ACTION_TIME_AND_DATE);
        startedServiceIntentFilter.addAction(MainActivity.ACTION_ARITHMETIC);
        startedServiceIntentFilter.addAction(MainActivity.ACTION_GEOMETRIC);
    }

    public void incrementLeftTextView(View view) {
        TextView leftTextView = findViewById(R.id.score_text_view_1);
        String text = leftTextView.getText().toString();
        int currValue = Integer.parseInt(text);
        leftTextView.setText(String.valueOf(currValue + 1));

        checkThreshold();
    }

    public void incrementRightTextView(View view) {
        TextView rightTextView = findViewById(R.id.score_text_view_2);
        String text = rightTextView.getText().toString();
        int currValue = Integer.parseInt(text);
        rightTextView.setText(String.valueOf(currValue + 1));

        checkThreshold();
    }

    private void checkThreshold() {
        TextView leftTextView = findViewById(R.id.score_text_view_1);
        String leftText = leftTextView.getText().toString();
        int leftValue = Integer.parseInt(leftText);

        TextView rightTextView = findViewById(R.id.score_text_view_2);
        String rightText = rightTextView.getText().toString();
        int rightValue = Integer.parseInt(rightText);

        if (leftValue + rightValue > THRESHOLD) {
            service = new Intent();
            service.putExtra(MainActivity.LEFT_FIELD_CONTENT_KEY, leftValue);
            service.putExtra(MainActivity.RIGHT_FIELD_CONTENT_KEY, rightValue);

            service.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01",
                    "ro.pub.cs.systems.eim.practicaltest01.PracticalTest01Service"));
            startService(service);

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        TextView leftTextView = findViewById(R.id.score_text_view_1);
        String leftText = leftTextView.getText().toString();

        TextView rightTextView = findViewById(R.id.score_text_view_2);
        String rightText = rightTextView.getText().toString();

        outState.putString(LEFT_FIELD_CONTENT_KEY, leftText);
        outState.putString(RIGHT_FIELD_CONTENT_KEY, rightText);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView leftTextView = findViewById(R.id.score_text_view_1);
        TextView rightTextView = findViewById(R.id.score_text_view_2);

        String leftText = savedInstanceState.getString(LEFT_FIELD_CONTENT_KEY);
        String rightText = savedInstanceState.getString(RIGHT_FIELD_CONTENT_KEY);

        leftTextView.setText(leftText);
        rightTextView.setText(rightText);
    }

    public void onNavigateClick(View view) {
        TextView leftTextView = findViewById(R.id.score_text_view_1);
        TextView rightTextView = findViewById(R.id.score_text_view_2);

        Integer leftValue = Integer.parseInt(leftTextView.getText().toString());
        Integer rightValue = Integer.parseInt(rightTextView.getText().toString());

        String text = String.valueOf(leftValue + rightValue);

        Intent i = new Intent(this, SecondaryActivity.class);
        i.putExtra(INTENT_CONTENT_KEY, text);
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String text = resultCode == RESULT_CANCELED ? "Canceled" : "Ok";

        Toast.makeText(this,
                "Secondary activity finished with status: " + text,
                Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "registered receiver");
        registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "unregistered receiver");
        unregisterReceiver(startedServiceBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stopService(service);
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
    }
}