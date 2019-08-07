package edu.pdx.cs410j.edeposit.secondApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        String message;
        String title=null;

        //get the intent that started this activity and extract the string
        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Bundle extras = intent.getExtras();
        if(extras != null)
            title = extras.getString("title");
            message=extras.getString("message");

        //Capture the layout's textview and set the string as its text
        TextView textView = (TextView) findViewById(R.id.title);
        if(title!=null)
            textView.setText(title);

        textView = (TextView) findViewById(R.id.body);
        textView.setText(message);
    }

    public void returnHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
