package edu.pdx.cs410J.edeposit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Comparable;

public class Appointment implements Comparable {

    public String description;
    public String beginTime;
    public String beginDate;
    public String endTime;
    public String endDate;
    public Date beginDateTime;
    public Date endDateTime;

    /**
     *
     * @param description
     *      A description of the appointment
     * @param beginDate
     *      The date when the appointment begins in the format mm/dd/yyyy or m/d/yyyy
     * @param beginTime
     *      The time at which the appointment begins in teh format hh:mm or h:mm
     * @param endDate
     *      The date when the appointment ends in the format mm/dd/yyyy or m/d/yyyy
     * @param endTime
     *      The time at which the appointment ends in teh format hh:mm or h:mm
     * @throws InvalidTimeException
     * throws invalid time exception if time or date is incorrectly formatted
     */
    public Appointment(String description, String beginDate, String beginTime, String beginTimeOfDay, String endDate, String endTime, String endTimeOfDay) throws InvalidTimeException{



        this.description=description;


        //check if date is empty
        ValidateTime vt= new ValidateTime();

        String result= vt.checkDate(beginDate);
        if(beginDate.equals(result)){
            this.beginDate= beginDate;
        }
        else{
            throw new InvalidTimeException(result);
        }

        result= vt.checkDate(endDate);
        if(endDate.equals(result)){
            this.endDate=endDate;
        }
        else{
            throw new InvalidTimeException(result);
        }

        result =vt.checkHourMin(beginTime);
        if(beginTime.equals(result)){
            this.beginTime= beginTime;
        }
        else{
            throw new InvalidTimeException(result);
        }

        result =vt.checkHourMin(endTime);
        if(endTime.equals(result)){
            this.endTime= endTime;
        }
        else{
            throw new InvalidTimeException(result);
        }


        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        if(beginTimeOfDay.equalsIgnoreCase("AM")){
            try{
                this.beginDateTime = dt.parse(beginDate + " " + beginTime + " " + "AM");
            }catch(ParseException e){
                throw new InvalidTimeException("could not parse time into date object:" + e);
            }
        }
        else if(beginTimeOfDay.equalsIgnoreCase("PM")){
            try{
                this.beginDateTime = dt.parse(beginDate + " " + beginTime + " " + "PM");
            }catch(ParseException e){
                throw new InvalidTimeException("could not parse time into date object:" + e);
            }
        }
        else{
            throw new InvalidTimeException("Must enter am or pm after start time");
        }


        if(endTimeOfDay.equalsIgnoreCase("AM")){
            try{
                this.endDateTime = dt.parse(endDate + " " + endTime + " " + "AM");
            }catch(ParseException e){
                throw new InvalidTimeException("could not parse time into date object:" + e);
            }
        }
        else if(endTimeOfDay.equalsIgnoreCase("PM")){
            try{
                this.endDateTime = dt.parse(endDate + " " + endTime + " " + "PM");
            }catch(ParseException e){
                throw new InvalidTimeException("could not parse time into date object:" + e);
            }
        }
        else{
            throw new InvalidTimeException("must enter am or pm after end Time");
        }

        if( this.beginDateTime.getTime() > this.endDateTime.getTime()){
            throw new InvalidTimeException("Appointment begin time must be before end time.");
        }


    }

    /**
     *
     * @return
     * returns <code>this.beginDate</code> attribute
     */
    public String getBeginDate(){
        return this.beginDate;
    }
    /**
     *
     * @return
     * returns <code>this.endDate</code> attribute
     */
    public String getEndDate(){
        return this.endDate;
    }

    /**
     *
     * @return
     * returns the time at which the appointment begins
     */

    public String getBeginTimeString() {
        //throw new UnsupportedOperationException("This method is not implemented yet");
        return this.beginTime;
    }

    /**
     *
     * @return
     * returns the time at which the appointment ends
     */

    public String getEndTimeString() {
        //throw new UnsupportedOperationException("This method is not implemented yet");
        return this.endTime;
    }

    /**
     *
     * @return
     * returns the description of the appointment
     */

    public String getDescription() {
        //return "not implemented";
        return this.description;
    }

    /**
     *
     * @return
     * returns a string containing the Description, beginDate, beginTime, endDate, and endTime, of an appointment.
     */
    public String print_appointment(){
        int f= DateFormat.SHORT;
        DateFormat df=DateFormat.getDateTimeInstance(f, f);

        return getDescription() + " from " + df.format(this.beginDateTime) + " until " + df.format(this.endDateTime);
    }

    /**
     *
     * @param o an appointment to be compared to
     * @return return is <0 if the appointment is less than <code>o</code>, return is >0 if the appointment is greater than <code>o</code>, return is 0 if the two appointments are equal in value
     */

    public int compareTo(Object o) {
        Appointment appt1= this;
        Appointment appt2= (Appointment) o;

        Date beginDate1=this.beginDateTime;
        Date beginDate2=appt2.beginDateTime;
        Date endDate1= this.endDateTime;
        Date endDate2= appt2.endDateTime;
        String description1= appt1.description;
        String description2= appt2.description;

        if(beginDate1.before(beginDate2)){
            return -1;
        }
        else if(beginDate2.before(beginDate1)){
            return 1;
        }
        else if(endDate1.before(endDate2)){
            return -1;
        }
        else if(endDate2.before(endDate1)){
            return 1;
        }
        else if(description1.compareTo(description2) < 0){
            return -1;
        }else if(description1.compareTo(description2) > 0){
            return 1;
        }
        else {
            return 0;
        }

    }


}
