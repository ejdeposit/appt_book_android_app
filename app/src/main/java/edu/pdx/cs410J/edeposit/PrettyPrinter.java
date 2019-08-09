package edu.pdx.cs410J.edeposit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.text.*;


public class PrettyPrinter {
    public String outputOption;

    public PrettyPrinter(String outputOption) {
        this.outputOption = outputOption;
    }

    public void dump(AppointmentBook appointmentBook) throws IOException {
        AppointmentBook book= appointmentBook;
        ArrayList<Appointment> apptList= new ArrayList(book.getAppointments());
        String apptString;

        int f= DateFormat.SHORT;
        DateFormat df=DateFormat.getDateTimeInstance(f, f);
        //SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        if(outputOption.equals("-")){
            System.out.println(book.getOwnerName()+"'s Appointments: ");
            for(Appointment appt: apptList){
                long begin=appt.beginDateTime.getTime();
                long end= appt.endDateTime.getTime();
                //long duration= Math.subtractExact(end, begin);
                long duration= end- begin;
                duration= duration/1000;
                duration= duration/60;
                System.out.println(appt.getDescription() + " from " + df.format(appt.beginDateTime) + " until " +  df.format(appt.endDateTime) + " (" + Long.toString(duration) + " minutes" + ")");
            }
        }
        else{
            FileWriter fOut = new FileWriter(this.outputOption);
            BufferedWriter bw = new BufferedWriter(fOut);

            bw.write(book.getOwnerName()+"'s Appointments: "+ "\n");
            bw.newLine();

            for(Appointment appt: apptList){
                //System.out.println(tod.format(appt.beginDateTime));
                long begin=appt.beginDateTime.getTime();
                long end= appt.endDateTime.getTime();
                //long duration= Math.subtractExact(end, begin);
                long duration= end-begin;
                duration= duration/1000;
                duration= duration/60;

                bw.write(appt.getDescription() + " from " + df.format(appt.beginDateTime) + " until " +  df.format(appt.endDateTime) + " (" + Long.toString(duration) +" minutes" + ")");
                bw.newLine();
                bw.newLine();
            }
            bw.close();
        }
    }

}
