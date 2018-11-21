/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;
import java.util.*;
/**
 *
 * @author Aoife
 */
public class Booking {
    private int bookingID;
    private int customerID;
    private int flightID;
    private int noAdults;
    private int noChild;
    private boolean baggage;
    private boolean priority;
    private double cost;
    private double totalPrice;
  
    
    public Booking(int bookingID,int customerID, int flightID, int noAdults, int noChildren, boolean Baggage, boolean priority, double cost){
      
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.flightID = flightID;
        this.noAdults = noAdults;
        this.noChild = noChildren;
        this.baggage = Baggage;
        this.priority = priority;
        this.cost = cost;
        this.totalPrice = cost;
    }
    public Booking(){}
    public void setbookingID(int id)               { this.bookingID = id; }
    public int  getbookingID()                     { return this.bookingID; }
    
    public void setcustomerID(int id)               { this.customerID = id; }
    public int  getcustomerID()                     { return this.customerID; }
    
    public void setflightID(int id)               { this.flightID = id; }
    public int  getflightID()                     { return this.flightID; }
    
    public void setNoAdults(int a)           { this.noAdults = a; }
    public int getNoAdults()                 { return this.noAdults; }
    
    public void setNoChild(int c)       { this.noChild = c; }
    public int getNoChild()             { return this.noChild; }
    
    public void setBaggage(boolean b)       { this.baggage = b; }
    public boolean getBaggage()             { return this.baggage; }
    
    public void setPriority(boolean p)       { this.priority = p; }
    public boolean getPriority()             { return this.priority; }
    
    public String toString() {
        String message = "Booking ID:\t\t" + this.bookingID + "\nNo. of Adults:\t" + this.noAdults + "\nNo. of Children:\t"
                         + this.noChild + "\nPriority Boarding:\t" + this.priority + "\nCheck in baggage:\t" + this.baggage;
        
        return message;
    }
    
    public void updateCost(String addCost){
       switch(addCost){
           case "newAdult": this.totalPrice += this.cost; break; // add another adult ticket to cost, must have minumum of 1 as base price
           case "newChild": this.totalPrice += this.cost/2;break; // add the cost of a child, half the cost of an adult ticket
           case "priority": this.totalPrice += 10.5;break; // add flatrate for priority boarding
           case "checkIn":  this.totalPrice += 35.5; break; // add flat rate for check in baggage
           case "abnormal": this.totalPrice += 88; break; // add flat rate for abnormal baggage 
           case "insurance":this.totalPrice += 115; break; //add travel insurance to total price 
           default: break;             
       }
    }
}