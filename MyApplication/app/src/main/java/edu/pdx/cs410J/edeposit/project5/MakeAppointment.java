package edu.pdx.cs410j.edeposit.secondApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class MakeAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
    }
    public void returnHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void makeAppointmentFromText(View view){
        //Intent intent = new Intent(this, MainActivity.class);

        EditText editText = (EditText) findViewById(R.id.owner);
        String ownerName = editText.getText().toString();

        String fileName=ownerName.trim();
        String[] nameArr=fileName.split(" ");

        fileName="";
        for(String name  : nameArr){
           fileName=fileName + name;
        }

        System.out.println(fileName);

        File file = new File(this.getFilesDir(), fileName);

        try{
            Writer writer= new FileWriter(file.toString());
            //Writer writer= file.openWriter();
            // writer= file.openOutputStream();
            writer.write(ownerName);
            //writer.write('\n');
            writer.flush();
            writer.close();

        }catch(IOException ex){
            System.err.println(ex);
        }



        String line="";
        System.out.println("read name from file");
        try{
            BufferedReader br =new BufferedReader(new FileReader(file.toString()));
            while (br.ready()){
                line=br.readLine();
                System.out.println(line);
            }
        }catch(IOException ex){
            System.err.println(ex);
        }

        //intent.putExtra(EXTRA_ownerName, ownerName);
        //startActivity(intent);
    }
}
