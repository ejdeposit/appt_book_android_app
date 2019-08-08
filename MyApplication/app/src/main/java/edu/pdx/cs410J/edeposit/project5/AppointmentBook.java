package edu.pdx.cs410j.edeposit.secondApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AppointmentBook {
    public String owner;
    public ArrayList<Appointment> appointmentList= new ArrayList<Appointment>();

    /**
     *
     * @param name
     *      The name of the owner of the appointment book
     */
    public AppointmentBook(String name){
        this.owner=name;
        //this.appointmentList=null;

    }

    /**
     *
     * @return
     *      returns a string that is the name of the owner of the appointment book
     */
    //Override
    public String getOwnerName() {
        return owner;
    }

    /**
     *
     * @return
     *      not implemented, returns null
     */
    //Override
    public Collection getAppointments() {
        return appointmentList;
    }

    /**
     *
     * @param appt
     *      Takes an Appointment as an argument and adds it to the appointment book
     */
    //Override
    public void addAppointment(Appointment appt) {
        //System.out.println(appt);
        appointmentList.add(appt);
    }

    public void sort_appointments(){
        int length= this.appointmentList.size();
        int min;
        Appointment apptJ=null;
        Appointment apptTemp=null;


        for(int i=0; i<length-1; i++){
            min=i;
            for(int j=i+1; j<length; j++){
                apptJ= (Appointment) this.appointmentList.get(j);

                if(apptJ.compareTo(this.appointmentList.get(min)) < 0){
                    min=j;
                }
            }
            //swap i and min
            apptTemp= (Appointment) this.appointmentList.get(i);
            this.appointmentList.set(i, this.appointmentList.get(min));
            this.appointmentList.set(min, apptTemp);
        }
    }
}
