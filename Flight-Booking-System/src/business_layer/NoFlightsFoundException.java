/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

/**
 * This class is used to notify FlightController if no flights were found 
 * during the searching phase.
 * 
 * @author John Rey Juele
 */
public class NoFlightsFoundException extends Exception {
    private String errorID;
    
    public NoFlightsFoundException(String message) {
        super(message);
        this.errorID = "NOFLIGHTS";
    }
    
    public String getErrorID() { return this.errorID; }
}
