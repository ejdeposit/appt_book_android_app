package edu.pdx.cs410J.edeposit;

public class InvalidTimeException extends Throwable {

    /**
     *
     * @param message contains information about the error associated with the time entered by the user
     */
    public InvalidTimeException(String message){
        super(message);
    }


}
