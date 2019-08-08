package edu.pdx.cs410J.edeposit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextParser {
    public String textFile;
    public String owner;

    /**
     *
     * @param textFile
     * The text file that will opened in parse() function and have its appointments loaded into the appointment book
     * @param owner
     * The owner of the appointment book from the command line argument.  Must match the owner of the text file.
     */
    public TextParser(String textFile, String owner){
        this.textFile=textFile;
        this.owner= owner;
    }

    /**
     *
     * @return
     * returns a blank appointment book if the the file does not exist.  If the file exists, contains appropriately formatted appointments, and the owner matches <code>this.owner</code>, An appointment book is created from the appointments and returned.
     * @throws ParserException
     * A <code>ParserExemption</code> is thrown if the the file is not formatted correctly, the file is blank, or the owner in the file does not match <code>this.owner</code>
     */

    public AppointmentBook parse() throws ParserException {

        String line="";
        String delimiter="`";
        String[] splitLine;
        //Boolean apptBookCreated= false;
        AppointmentBook book=null;
        String fileOwner;
        String description;
        String beginDate;
        String beginTime;
        String endDate;
        String endTime;
        String beginTimeOfDay=null;
        String endTimeOfDay=null;

        ArrayList<Appointment> fileAppointments= new ArrayList<>();

        try{
            BufferedReader br =new BufferedReader(new FileReader(this.textFile));
            while (br.ready()){
                line=br.readLine();
                //System.out.println(line);

                //need to validate that line is in correct format using split
                splitLine=line.split(delimiter);
                if(splitLine.length != 8){
                    throw new ParserException("Improper file format");
                }

                fileOwner=splitLine[0];
                description= splitLine[1];
                beginDate = splitLine[2];
                beginTime= splitLine[3];
                beginTimeOfDay=splitLine[4];
                endDate= splitLine[5];
                endTime= splitLine[6];
                endTimeOfDay=splitLine[7];

                if(!fileOwner.equals(this.owner)){
                    throw new ParserException("Owner in text file different from argument given");
                }
                else{
                    Appointment appt;
                    try{
                        appt= new Appointment(description, beginDate, beginTime, beginTimeOfDay, endDate, endTime, endTimeOfDay);
                    }catch(InvalidTimeException e){
                        throw new ParserException("Error found while parsing file:" + e);
                    }

                    //System.out.println(appt.toString());
                    fileAppointments.add(appt);
                }
            }
        }catch(FileNotFoundException nf){
            return new AppointmentBook(this.owner);
        }
        catch(IOException ex){
            return new AppointmentBook(this.owner);

        }

        book= new AppointmentBook(this.owner);

        if(fileAppointments.size()>0){
            for(Appointment appt: fileAppointments){
                book.addAppointment(appt);

            }
        }else{
            throw new ParserException("Cannot parse File");
        }

        return book;
    }
}

