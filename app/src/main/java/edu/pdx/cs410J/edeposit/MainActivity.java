package edu.pdx.cs410J.edeposit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.io.File;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE= "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when user taps the send button */
    /*
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
*/

    public void newAppointment(View view){
        Intent intent = new Intent(this, MakeAppointmentActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showAppointments(View view){
        Intent intent = new Intent(this, PrintAppointmentBookActivity.class);
        startActivity(intent);
    }

    public void searchAppointments(View view){
        Intent intent = new Intent(this, SearchAppointmentsActivity.class);
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
    public void readMe(View view){
        String message = "New Appointment:\nTo create a new appointment click on the new appointment button and enter the appointment book owner's name, description of the appointment, begin time of the appointment and the end time.  The name and description can be one or more words, but must not include any back ticks \"`\".  The begin time and end time must use the following format: \"MM/dd/yyyy HH:mm a\", where a is the either AM or PM\n\nView All Appointments:\nTo view all appointments for an owner click on the View All Appointments button and enter the name of the owner.\n\nSearch Appointment Book:\nTo view only appointments that fall within a specified range, click on the Search Appointment Book button.  Enter the appointment book owner's name, and the time range.  The begin time and end time must use the same format that was used to create a new appointment: \"MM/dd/yyyy HH:mm a\"\n\nREADME:\nDisplays the README for the application\n\nDELETE ALL APPOINTMENT BOOKS:\nclicking the DELETE ALL APPOINTMENT BOOKS button will delete all appointment books, owner inforamtion from the phone. This button is inteneded for manual testing purposes.\n\n";
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("title", "README");
        startActivity(intent);
    }
}
