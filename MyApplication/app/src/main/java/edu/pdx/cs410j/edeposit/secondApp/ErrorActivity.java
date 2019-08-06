package edu.pdx.cs410j.edeposit.secondApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        String message="";

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null)
            message = extras.getString("errorMessage");

        //Capture the layout's textview and set the string as its text
        TextView textView = (TextView) findViewById(R.id.errMsg);
        textView.setText(message);

    }
}
