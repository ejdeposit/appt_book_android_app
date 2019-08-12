package edu.pdx.cs410J.edeposit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchAppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointments);
    }

    public void searchForAppointmentsWithinRange(View view){
        String ownerName=null;
        String beginTime=null;
        String endTime=null;

        EditText editText = (EditText) findViewById(R.id.owner);
        ownerName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.beginTime);
        beginTime = editText.getText().toString();

        editText = (EditText) findViewById(R.id.endTime);
        endTime = editText.getText().toString();

        if(ownerName.equals("") || beginTime.equals("") || endTime.equals("")){
            String message = "Missing required field";
            System.err.println(message);
            TextView textView = (TextView) findViewById(R.id.errorBox);
            textView.setText("Error: "+ message);
            return;
        }

        String fileName = ownerName.trim();
        String[] nameArr = fileName.split(" ");
        fileName = "";
        for (String name : nameArr) {
            fileName = fileName + name;
        }
        fileName=fileName+".apb";

        File file = new File(this.getFilesDir(), fileName);

        AppointmentBook book = null;

        if (file.exists()) {
            TextParser textParser= new TextParser(file.toString(), ownerName);
            try{
                book = textParser.parse();
            }
            catch(ParserException e){
                System.err.println(e);
                String message = e.toString();
                Intent intent = new Intent(this, DisplayMessageActivity.class);
                intent.putExtra("message", message);
                intent.putExtra("title", "An Error Occured!");
                startActivity(intent);
                return;
            }
        }
        else {
            String message = "No appointment book found for specified owner";
            System.err.println(message);
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("title", "Appointment Book Search");
            startActivity(intent);
            return;
        }

        ArrayList<Appointment> allAppointments;
        ArrayList<Appointment> foundAppointments= new ArrayList<Appointment>();
        Date rangeStart=null;
        Date rangeEnd=null;
        //boolean timeError=false;

        ValidateTime vt= new ValidateTime();
        try{
            rangeStart= vt.validateAndMakeDate(beginTime);
        }catch(InvalidTimeException e){
            System.err.println(e);
            String message = e.toString();
            TextView textView = (TextView) findViewById(R.id.errorBox);
            textView.setText("Error: " + message);
            return;
        }
        try{
            rangeEnd= vt.validateAndMakeDate(endTime);
        }catch(InvalidTimeException e){
            System.err.println(e);
            String message = e.toString();
            TextView textView = (TextView) findViewById(R.id.errorBox);
            textView.setText("Error: "+ message);
            return;
        }

        //make sure beginTime is begore endtime
        if( rangeStart.getTime() > rangeEnd.getTime()){
            String message = "start time must be before end time.";
            TextView textView = (TextView) findViewById(R.id.errorBox);
            textView.setText("Error: "+ message);
            return;
        }

        int f= DateFormat.SHORT;
        DateFormat df=DateFormat.getDateTimeInstance(f, f);
        String line="";
        if (book!=null){
            allAppointments= new ArrayList(book.getAppointments());
            for(Appointment appt: allAppointments){
                if( appt.beginDateTime.getTime()>= rangeStart.getTime() && appt.endDateTime.getTime()<= rangeEnd.getTime()){
                    foundAppointments.add(appt);
                }
            }
            if(foundAppointments.size()>0){
                line= ownerName + "'s Appointments:"+ "\n\n";
                for(Appointment appt: foundAppointments){
                    long begin=appt.beginDateTime.getTime();
                    long end= appt.endDateTime.getTime();
                    long duration= end-begin;
                    duration= duration/1000;
                    duration= duration/60;
                    String apptStr = appt.getDescription() + " from " + df.format(appt.beginDateTime) + " until " +  df.format(appt.endDateTime) + " (" + Long.toString(duration) +" minutes" + ")";
                    line= line + apptStr + "\n\n";
                    //line= line + appt.print_appointment() + "\n";
                }
            }
            else{
                line= "No appointments found for " + ownerName + " within specified range";
            }
        }
        else{
            line= "No appointmentbook for owner: " + ownerName;
        }

        String message = line;
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("title", "Appointment Book Search");
        startActivity(intent);
        return;

    }

    public void returnHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
