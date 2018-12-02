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
public class insuranceDecorator extends BookingDecorator {
       public boolean insurance;
       public Booking thisBook;
       public insuranceDecorator(Booking decBook, boolean ins){
       super(decBook);
       this.insurance = ins;
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
      if(this.insurance){
          cost += 99.99;
      }
      return cost;
    }
    @Override
    public String toString(){
       return thisBook.toString() + "," + this.insurance + "\n";
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
