/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;
import DB_Layer.Dao;
import DB_Layer.DaoFactory;

/**
/**
 *
 * @author Aoife
 */
public class BookingManager {
      private Dao<Booking> dao;
    
    public BookingManager() {
        this.dao = DaoFactory.getDao(DaoFactory.BOOKING);
    }
    
    public void addBooking(int bookingID,int customerID, int flightID, int noAdults, int noChildren, Boolean Baggage, Boolean priority, double cost){
        dao.add(new Booking(bookingID,customerID,flightID,noAdults,noChildren,Baggage,priority,cost));
    }
    
   
    public String searchBooking(int id) {
        Booking booking = dao.get(id);
        
        if(booking == null)
            return "Booking does not exist";
        else
            return booking.toString();
    }
    
}