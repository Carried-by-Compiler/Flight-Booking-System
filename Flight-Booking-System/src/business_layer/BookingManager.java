/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;
import DB_Layer.Dao;
import DB_Layer.DaoFactory;
import design_decorator.Book;
import design_decorator.BookingDecorator;
import design_decorator.Seating;
import design_decorator.baggageDecorator;
import design_decorator.insuranceDecorator;
import design_decorator.seatingDecorator;

/**
/**
 *
 * @author Aoife
 */
public class BookingManager {
      private Dao<Book> dao;
    
    public BookingManager() {
        this.dao = DaoFactory.getDao(DaoFactory.BOOK);
    }
    
    public void addBooking(int user, String [] info, String seatType, Boolean baggage, Boolean Insurance){
         Seating stype= Seating.valueOf(seatType);
         if(!(baggage) && !(Insurance)){         
          dao.add(new seatingDecorator(new Booking(user, info),stype));
        }else{
           if((baggage) && (Insurance)){               
             dao.add(new baggageDecorator(new insuranceDecorator((new seatingDecorator(new Booking(user, info),stype)),Insurance),baggage));
           }else{
              if((baggage) && !(Insurance)){              
               dao.add(new baggageDecorator((new seatingDecorator(new Booking(user, info),stype)),baggage));
              
           }else{
                 if(!(baggage) && (Insurance)){
                     dao.add(new insuranceDecorator((new seatingDecorator(new Booking(user, info),stype)),Insurance)); 
              }else{  
                     dao.add(new Booking(user,info));                     
                 }}
           }
        }
        
    }
    
    public void printInfo(String [] info){
      System.out.println("printing");
      for(int z = 0; z < info.length;z++){
         String [] itemValues = info[z].split(",");
         System.out.println("Flight Info: ");
         for(int y =0; y < itemValues.length;y++){
           System.out.print(itemValues[y]+",");
         }
      }
    
    }
   
    public String searchBooking(int id) {
        Book booking = dao.get(id);
        
        if(booking == null)
            return "Booking does not exist";
        else
            return booking.toString();
    }
    
}