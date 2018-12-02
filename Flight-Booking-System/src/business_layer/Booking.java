/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;
import design_decorator.Book;
/**
 *
 * @author Aoife
 */
public class Booking implements Book {
    private int bookingID;
    private int customerID;
    private String [] flightInfo;
    private double totalCost;
    private double baseCost;
    private boolean returnFlight;
    public Booking(){}
    public Booking(int userID, String[]flightInfo,boolean returnFlight){
        this.bookingID = (int)(Math.random() * 5000 + 1);
        this.customerID = userID;
        this.flightInfo= flightInfo;
        this.totalCost = calcTotalCost();
        this.baseCost = calcTotalCost();
        this.returnFlight = returnFlight;
    }   
    @Override
    public double calcTotalCost(){
       Double cost = 0.0;
       if(this.returnFlight){
       String [] flightD = this.flightInfo[0].split(",");
       String [] flightA = this.flightInfo[1].split(",");
       String costD = flightD[flightD.length-1];
       String costA = flightA[flightA.length-1];
    
       cost+= Double.parseDouble(costD);
       cost+= Double.parseDouble(costA);
       }else{
           String [] flightD = this.flightInfo[0].split(",");
           cost+= Double.parseDouble(flightD[flightD.length-1]);
       }
       return cost;    
    }
    public int getBookingId(){return this.bookingID;}
    public void setTotalPrice(double p){this.totalCost = p;}
    public double getBaseCost(){return this.baseCost;}
    @Override
    public double getTotalCost(){return this.totalCost;}
    @Override
    public String toString() {
        if(returnFlight){  return  this.bookingID + ","+this.customerID + "," + flightInfo[0] +  ","+ flightInfo[1] + "," + this.totalCost;   }
        else{
            return  this.bookingID + ","+this.customerID +  ","+ flightInfo[0] + "," + this.totalCost;  
        }
     
    }
   
}