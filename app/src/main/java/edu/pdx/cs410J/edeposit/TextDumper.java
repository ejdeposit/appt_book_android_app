package edu.pdx.cs410J.edeposit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TextDumper {
    public String textFile;

    /**
     *
     * @param textFile
     * The text file that the appointment book should be written to.
     */
    public TextDumper(String textFile){
        this.textFile=textFile;
    }

    /**
     *
     * @param book
     * Takes ann abstract appointment book as a argument and dumps its contents to the file specified in the constructor
     *
     * @throws IOException
     * An IOEXception is thrown if the writer can not open text file specified
     *
     *
     */

    public void dump(AppointmentBook book) throws IOException {
        String delimiter= "`";
        String apptStr=null;
        ArrayList<Appointment> apptList= new ArrayList(book.getAppointments());

        SimpleDateFormat tod= new SimpleDateFormat("a");


        //FileWriter fOut = new FileWriter("./appointmentBookDump.txt");
        FileWriter fOut = new FileWriter(this.textFile);
        BufferedWriter bw = new BufferedWriter(fOut);
        // need to add owner to string and dates to appt.toString()

        for(Appointment appt: apptList){
            //System.out.println(tod.format(appt.beginDateTime));

            apptStr=book.getOwnerName() + delimiter + appt.getDescription() + delimiter + appt.getBeginDate() + delimiter + appt.getBeginTimeString() + delimiter + tod.format(appt.beginDateTime) + delimiter + appt.getEndDate()  + delimiter + appt.getEndTimeString()+ delimiter + tod.format(appt.endDateTime);

            //fOut.write(apptStr);
            bw.write(apptStr);
            bw.newLine();
        }
        bw.close();
    }
}

