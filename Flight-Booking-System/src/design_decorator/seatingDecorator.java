/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design_decorator;

import business_layer.Booking;


/**
 *
 * @author Aoife
 */
public class seatingDecorator extends BookingDecorator {
    protected Seating seatType;
    Booking thisBook;
    public seatingDecorator(Booking decBook, Seating seat){
       super(decBook);
       this.seatType = seat;
       this.thisBook = decBook;
       this.thisBook.setTotalPrice(calcTotalCost());
      
    }
    @Override
    public double getTotalCost(){
       return this.thisBook.getTotalCost();
    }
    @Override
    public double calcTotalCost(){
       Double curPrice = thisBook.getBaseCost();
       switch(this.seatType){
           case FIRST: curPrice += 130.5;break;
           case BUSINESS: curPrice += 80.3;break;
           case ECONOMY: curPrice += 0.0;break;
       }
       return curPrice;
    }
    @Override
    public String toString(){
       return thisBook.toString() + "," + this.seatType + "\n";
    }

    @Override
    public int getBookingId() {
        return thisBook.getBookingId();
    }

    @Override
    public double getBaseCost() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
