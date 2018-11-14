/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Layer;

import business_layer.Flight;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author John Rey Juele
 */
public class FlightsDAO implements Dao<Flight>{
    
    private String filename;
    
    private BufferedReader br;
    private BufferedWriter bw;
    
    public FlightsDAO() {
        this.filename = "./Database/Flights.csv";
    }

    @Override
    public Flight get(int id) {
        String line;
        String[] lineSplit = new String[6];
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AirlineDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioException) {
            Logger.getLogger(AirlineDAO.class.getName()).log(Level.SEVERE, null, ioException);
        }
        
        if(found) {
            LocalDateTime dTime = LocalDateTime.parse(lineSplit[4]);
            LocalDateTime aTime = LocalDateTime.parse(lineSplit[5]);
            return new Flight(Integer.parseInt(lineSplit[0]), Integer.parseInt(lineSplit[1]),
                lineSplit[2], lineSplit[3], dTime, aTime, Double.parseDouble(lineSplit[6]));
        } else {
            return null;
        }
    }

    @Override
    public void add(Flight n) {
        
        String line = "";
        String[] details = n.getDetailsAsArray();
        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            Flight existing = this.get(Integer.parseInt(details[0]));
            
            if(existing == null) {
                
                for(int i = 0; i < details.length; i++) {
                    line += details[i] + ",";
                }
                
                line = line.substring(0, line.length() - 1);
                line += "\n";
                bw.append(line);
                System.out.println("Successfully added flight: " + details[0]);
                bw.close();
                
            } else {                
                System.out.println("Flight already exists!");
            }
        } catch(IOException e) {
            System.out.println(e.toString());
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
    public ArrayList<Flight> getAll() {
        Flight f;
        ArrayList<Flight> flights = new ArrayList<Flight>();
        String line;
        String[] lineSplit = new String[6];
        
        try {
            br = new BufferedReader(new FileReader(filename));
            
            while((line = br.readLine()) != null) {
                lineSplit = line.split(",");
                
                LocalDateTime dTime = LocalDateTime.parse(lineSplit[4]);
                LocalDateTime aTime = LocalDateTime.parse(lineSplit[5]);
                f = new Flight(Integer.parseInt(lineSplit[0]), Integer.parseInt(lineSplit[1]),
                    lineSplit[2], lineSplit[3], dTime, aTime, Double.parseDouble(lineSplit[6]));
                
                flights.add(f);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlightsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        
        return flights;
    }

    @Override
    public Flight get(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
