package edu.pdx.cs410J.edeposit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.os.Bundle;

public class MakeAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
    }

    public void returnHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void makeAppointmentFromText(View view) {
        AppointmentBook book=null;
        Appointment appointment=null;
        String ownerName=null;
        String description=null;
        String beginTime=null;
        String endTime=null;

        EditText editText = (EditText) findViewById(R.id.owner);
        ownerName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.description);
        description = editText.getText().toString();

        editText = (EditText) findViewById(R.id.beginTime);
        beginTime = editText.getText().toString();

        editText = (EditText) findViewById(R.id.endTime);
        endTime = editText.getText().toString();

        if(description.equals("") || ownerName.equals("")){
            String message = "Missing required field";
            System.err.println(message);
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("title", "An Error Occured!");
            startActivity(intent);
            return;
        }

        String beginTimeArr[] = beginTime.split(" ");
        if(beginTimeArr.length != 3){
            String message = "Begin Time must include date, time, and time of Day";
            System.err.println(message);
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("title", "An Error Occured!");
            startActivity(intent);
            return;
        }

        String endTimeArr[] = endTime.split(" ");
        if(endTimeArr.length != 3){
            String message = "End Time must include date, time, and time of Day";
            System.err.println(message);
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("title", "An Error Occured!");
            startActivity(intent);
            return;
        }

        try{
            appointment= new Appointment(description, beginTimeArr[0], beginTimeArr[1], beginTimeArr[2], endTimeArr[0], endTimeArr[1], endTimeArr[2]);

        }
        catch(InvalidTimeException e){
            System.err.println(e);
            String message = e.toString();
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("title", "An Error Occured!");

            startActivity(intent);
            return;
        }

        String fileName = ownerName.trim();
        String[] nameArr = fileName.split(" ");
        fileName = "";
        for (String name : nameArr) {
            fileName = fileName + name;
        }
        fileName=fileName+".apb";

        System.out.println("File Name to store Appointment Book: " + fileName);

        File file = new File(this.getFilesDir(), fileName);

        if (file.exists()) {
            TextParser textParser= new TextParser(file.toString(), ownerName);
            try{
                book = textParser.parse();
                if(appointment != null){
                    book.addAppointment(appointment);
                }

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
            //create new appointment book and write to file
            book= new AppointmentBook(ownerName);
            book.addAppointment(appointment);
        }


        TextDumper textDumper= new TextDumper(file.toString());
        try{
            textDumper.dump(book);
        }
        catch(IOException e){
            System.err.println(e);
            String message = e.toString();
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("title", "An Error Occured!");
            startActivity(intent);
            return;
        }

        System.out.println("read file after dump");
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.toString()));
            while (br.ready()) {
                line = br.readLine();
                System.out.println(line);
            }
        }
        catch (IOException ex) {
            System.err.println(ex);
            String message = ex.toString();
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("title", "An Error Occured!");

            startActivity(intent);
        }
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = appointment.print_appointment();
        intent.putExtra("message", message);
        intent.putExtra("title", "Appointment created!");
        startActivity(intent);
    }

    /*
            try {
                Writer writer = new FileWriter(file.toString());
                //Writer writer= file.openWriter();
                // writer= file.openOutputStream();
                writer.write(ownerName);
                //writer.write('\n');
                writer.flush();
                writer.close();
            }
            catch (IOException ex) {
                System.err.println(ex);
                Intent intent = new Intent(this, ErrorActivity.class);
                String message = ex.toString();
                intent.putExtra("errorMessage", message);
                startActivity(intent);
            }
            */
}
