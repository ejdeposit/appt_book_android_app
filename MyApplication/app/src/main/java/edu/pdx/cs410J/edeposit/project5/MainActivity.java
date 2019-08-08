package edu.pdx.cs410j.edeposit.secondApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE= "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when user taps the send button */
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void newAppointment(View view){
        Intent intent = new Intent(this, MakeAppointmentActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void deleteAllAppointmentBooks(View view){
        File directory = this.getFilesDir();
        String[] fileList=directory.list();
        for(String fileName: fileList){
            File file = new File(this.getFilesDir(), fileName);
            System.out.println("abstract path name from list(): " + fileName);
            System.out.println("pathname string of abstract pathname: "+ file.toString());
            if(!fileName.contains("instant-run")){
                file.delete();
            }
        }


    }
}
