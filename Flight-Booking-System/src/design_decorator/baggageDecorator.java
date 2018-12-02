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
public class baggageDecorator extends BookingDecorator {
       public boolean baggage;
       public Booking thisBook;
       public baggageDecorator(Booking decBook, boolean ins){
       super(decBook);
       this.baggage = ins;
       this.thisBook = decBook;
       this.thisBook.setTotalPrice(calcTotalCost());
      
    }
    @Override
    public double getTotalCost(){
       return this.thisBook.getTotalCost();
    }
    @Override
    public double calcTotalCost(){
        double cost = this.thisBook.getBaseCost();
      if(this.baggage){
          cost += 55.10;
      }
      return cost;
    }
    @Override
    public String toString(){
       return thisBook.toString() + "," + this.baggage + "\n";
    }

    @Override
    public int getBookingId() {
        return thisBook.getBookingId();
    }

    
    @Override
    public double getBaseCost() {
        return this.decoratedBooking.getBaseCost();
    }
}