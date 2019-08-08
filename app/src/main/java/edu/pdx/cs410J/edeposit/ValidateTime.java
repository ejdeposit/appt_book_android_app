package edu.pdx.cs410J.edeposit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ValidateTime {
    /**
     *
     * @param date is date in mm/dd/yyyy or m/d/yyyy format
     * @return returns the date if it is in the correct format, or a string containing an error message that explains which part of the date is invalid.
     */
    public String checkDate(String date){

        //check if date is empty
        String[] dateArr=date.split("/");

        for(int i=0; i<dateArr.length; i++){
            for(int j=0; j<dateArr[i].length(); j++){

                if(!Character.isDigit(dateArr[i].charAt(j))){
                    return ("Time String contains non-digits");
                }
            }
        }

        if(date.equals("")){
            return "Empty begin or end time";
        }
        //check if date has correct number of slashes
        else if(dateArr.length !=3){
            return "Date does not contain month, day, and year";
        }
        //check mm or m for begin an end date
        else if(dateArr[0].length() <1 || dateArr[0].length() > 2 ){
            return("Incorrect number of digits in month or day");
        }
        //dd or d for begin and end date
        else if(dateArr[1].length() <1 || dateArr[1].length() > 2 ){
            return("Incorrect number of digits in month or day");
        }
        //check to see if valid month number
        else if(Integer.parseInt(dateArr[0]) >12 || Integer.parseInt(dateArr[0]) < 1){
            return("Incorrect number of digits in month");
        }
        //check to see if valid day number
        else if(Integer.parseInt(dateArr[1]) >31 || Integer.parseInt(dateArr[1]) < 1){
            return("Incorrect number of digits in month");
        }
        //check to year is yyyy
        else if(dateArr[2].length() != 4){
            return("Incorrect number of digits in year");
        }
        else if(Integer.parseInt(dateArr[2]) < 0){
            return("Year must be greater than or equal to zero");
        }
        else{
            return date;
        }
    }

    /**
     *
     * @param time is a string in the format hh:mm or h:mm
     * @return is the time string if it is formated correctly or an error message explaining what is wrong with the format.
     */
    public String checkHourMin(String time){

        String[] TimeArr=time.split(":");

        for(int i=0; i<TimeArr.length; i++){
            for(int j=0; j<TimeArr[i].length(); j++){
                if(!Character.isDigit(TimeArr[i].charAt(j))){
                    return("Time String contains non-digits");
                }
            }
        }
        if(time.equals("")){
            return("Time argument is empty");
        }
        else if(TimeArr.length != 2){
            return("Incorrect format for time");
        }
        // if not 1-2 digits in hour or min of time
        else if(TimeArr[0].length() == 0 || TimeArr[0].length() > 2 ||
                TimeArr[1].length() == 0 || TimeArr[1].length() > 2 ){
            return("Incorrect number of digits in hour or minutes");
        }
        else if(Integer.parseInt(TimeArr[0])< 0 || Integer.parseInt(TimeArr[0]) > 13){
            return("Invalid hour for time");
        }
        else if(Integer.parseInt(TimeArr[1])< 0 || Integer.parseInt(TimeArr[1]) > 59){
            return("Invalid minutes for time");
        }
        else{
            return time;
        }
    }

    /**
     * take a time as a string and turns it into a date object
     * @param time is string containing the date time and am/pm in the format "mm/dd/yyyy hh:mm a"
     * @return Date object of the date and time specified by the string string
     * @throws InvalidTimeException
     */
    public Date validateAndMakeDate(String time)throws InvalidTimeException{
        ValidateTime vt=new ValidateTime();
        String[] timeArr=time.split(" ");

        String date= timeArr[0];
        String clockTime= timeArr[1];
        String timeOfDay=timeArr[2];

        //check date
        String result= vt.checkDate(date);
        if(!date.equals(result)){
            throw new InvalidTimeException(result);
        }

        //check time
        result= vt.checkHourMin(clockTime);
        if(!clockTime.equals(result)){
            throw new InvalidTimeException(result);
        }

        //check time of day, parse, and return
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        if(timeOfDay.equalsIgnoreCase("AM")){
            try{
                return dt.parse(date + " " + clockTime + " " + "AM");
            }catch(ParseException e){
                throw new InvalidTimeException("could not parse time into date object:" + e);
            }
        }
        else if(timeOfDay.equalsIgnoreCase("PM")){
            try{
                return dt.parse(date + " " + clockTime + " " + "PM");
            }catch(ParseException e){
                throw new InvalidTimeException("could not parse time into date object:" + e);
            }
        }
        else{
            throw new InvalidTimeException("Must enter am or pm after start time");
        }

    }

}
