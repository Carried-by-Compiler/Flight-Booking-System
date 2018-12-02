/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Layer;

import business_layer.Booking;
import design_decorator.Book;
import design_decorator.seatingDecorator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Aoife
 */
public class BookingDAO implements Dao<Book>{
/**
 * Will be accepting the information from the search GUI 
 * The information will then be displayed and ONLY when the user selects buy move to new window
 * New window will then display policy additions i.e number of tickets, baggage, priority ect 
 * When 'booking' will not store booking until purchased. 
 * 
 */
    private String filename;
    private BufferedReader br;
    private BufferedWriter bw;
    
    
    public BookingDAO(){
        this.filename = "./Database/Booking.csv";
        

    }
    
    @Override
    public Booking get(int id) {
      
      
      String line;
      String[] lineSplit = new String[3];
      boolean found = false;
      
        try {
            br = new BufferedReader(new FileReader(filename));
            
            while(!found && (line = br.readLine()) != null) {
                lineSplit = line.split(",");
                if(Integer.parseInt(lineSplit[0]) == id) {
                    found = true;
                }
            }
            
            br.close();
        }
        
        catch (FileNotFoundException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioException) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ioException);
        }
        
        if(found) {
           return new Booking();
        } 
        else {
            return null;
        }
    }

    @Override
    public Booking get(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void add(Book n) {
        int bookingid = n.getBookingId();
        String info = n.toString();
        try {
            Booking existingBooking = this.get(bookingid);
            
            if(existingBooking != null) {
                // Fix error message
                System.out.println(bookingid + " already exists!");
            } else {
                bw = new BufferedWriter(new FileWriter(filename, true));
                bw.append(info);
                bw.close();
                
                System.out.println("Successfully added: " + bookingid);
            }
        } catch (IOException ex) {
            Logger.getLogger(AirlineDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Book> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    
}