package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    private String textViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        TextView textView = findViewById(R.id.button_action_text_view);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return;

        textView.setText("Buttons pressed "
                + extras.getString(MainActivity.INTENT_CONTENT_KEY)
                + " times."
        );
    }

    public void cancelAction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void okAction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }
}