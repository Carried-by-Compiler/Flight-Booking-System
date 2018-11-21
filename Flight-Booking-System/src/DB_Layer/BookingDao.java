/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Layer;

import business_layer.Booking;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eogha
 */
public class BookingDao implements Dao<Booking>{

    private String filename;
    private BufferedReader br;
    private BufferedWriter bw;
    
    
    public BookingDao(){
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
            return new Booking(Integer.parseInt(lineSplit[0]), lineSplit[1], lineSplit[2]);
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
    public void add(Booking n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public ArrayList<Booking> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
