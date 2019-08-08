package edu.pdx.cs410j.edeposit.secondApp;

public class ParserException extends Throwable {
    /**
     *
     * @param message contains information about the error associated with the time entered by the user
     */
    public ParserException(String message){
        super(message);
    }
}
