package DB_Layer;

import business_layer.Airline;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John Rey Juele
 */
public class AirlineDAO implements Dao<Airline> {
    
    private String filename;
    
    private BufferedReader br;
    private BufferedWriter bw;
    
    public AirlineDAO() {
        // this should be stored in the database handler
        this.filename = "./Database/Airlines.csv";
    }
    
    @Override
    public Airline get(int id) {
        
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AirlineDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioException) {
            Logger.getLogger(AirlineDAO.class.getName()).log(Level.SEVERE, null, ioException);
        }
        
        if(found) {
            return new Airline(Integer.parseInt(lineSplit[0]), lineSplit[1],
                lineSplit[2]);
        } else {
            return null;
        }
    }

    @Override
    public void add(Airline n) {
        int id = n.getID();
        String name = n.getName();
        String password = n.getPassword();
        
        try {
            Airline existingAirline = this.get(id);
            
            if(existingAirline != null) {
                // Fix error message
                System.out.println(name + " already exists!");
            } else {
                bw = new BufferedWriter(new FileWriter(filename, true));
                String newLine = id + "," + name + "," + password + "\n";
                bw.append(newLine);
                bw.close();
                
                System.out.println("Successfully added: " + name);
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
    
    /*
    public int storeAirlineDetails(Airline a) {
        int code = -1;
        
        int id = a.getID();
        String name = a.getName();
        String password = a.getPassword();
        
        Airline existing = getAirline(id);
        
        if(existing != null) {
            code = 0;
        } else {
            try {
                bw = new BufferedWriter(new FileWriter(filename, true));
                
                String newLine = id + "," + name + "," + password + "\n";
                bw.append(newLine);
                bw.close();
            
                code = 1;
            } catch(IOException e) {
                code = -1;
            }
        }
        
        
        return code;
    }
    
    public Airline getAirline(int id) {
        
        String line;
        boolean found = false;
        String splitLine[] = new String[3];
        Airline a;
        
        try {
            br = new BufferedReader(new FileReader(filename));
            
            while((line = br.readLine()) != null && !found) {
                splitLine = line.split(",");
                if(Integer.parseInt(splitLine[0]) == id) {
                    found = true;
                }
            }
            a = new Airline(Integer.parseInt(splitLine[0]), splitLine[1], splitLine[2]);
            br.close();
        } catch(IOException e) {
            return null;
        }
        
        if(found) {
            return a;
        } else {
            return null;
        }
    }
*/

    

    
}
