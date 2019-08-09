package edu.pdx.cs410J.edeposit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PrintAppointmentBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_appointment_book);
    }

    public void returnHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void printAllAppointmentsForOwner(View view){
        String ownerName;

        EditText editText = (EditText) findViewById(R.id.owner);
        ownerName = editText.getText().toString();
        System.out.println(ownerName);

        if(ownerName.equals("")){
            String message = "Please Enter an appointment book owner";
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

        System.out.println("File Name to store Appointment Book: " + fileName);

        File dumpFile = new File(this.getFilesDir(), fileName);

        if (!dumpFile.exists()) {
            String message = "No appointment book found for specified owner";
            System.err.println(message);
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("message", message);
            intent.putExtra("title", "All Appointments");
            startActivity(intent);
            return;

        }
        else{
            File prettyFile = new File(this.getFilesDir(), "prettyFile");
            PrettyPrinter prettyPrinter = new PrettyPrinter(prettyFile.toString());

            TextParser textParser= new TextParser(dumpFile.toString(), ownerName);
            AppointmentBook book=null;

            try{
                book =textParser.parse();
            }catch(ParserException e){
                System.err.println(e);
                String message = e.toString();
                Intent intent = new Intent(this, DisplayMessageActivity.class);
                intent.putExtra("message", message);
                intent.putExtra("title", "An Error Occured!");
                startActivity(intent);
                return;
            }

            try{
                prettyPrinter.dump(book);
            }catch(IOException e){
                System.err.println(e);
                String message = e.toString();
                Intent intent = new Intent(this, DisplayMessageActivity.class);
                intent.putExtra("message", message);
                intent.putExtra("title", "An Error Occured!");
                startActivity(intent);
                return;
            }


            System.out.println("read file after pretty dump");
            String line = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(prettyFile.toString()));
                while (br.ready()) {
                    line = line + br.readLine() + "\n";
                    //System.out.println(line);
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

            System.out.println(line);

            Intent intent = new Intent(this, DisplayMessageActivity.class);
            String message = line;
            intent.putExtra("message", message);
            intent.putExtra("title", "View All Appointments");
            startActivity(intent);

        }

    }
}
