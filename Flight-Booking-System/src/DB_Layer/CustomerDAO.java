/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Layer;

import business_layer.Customer;
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
 * @author eoghan
 */
public class CustomerDAO implements Dao<Customer> {
    
    private String filename;
    
    private BufferedReader br;
    private BufferedWriter bw;
    
    public CustomerDAO(){
        this.filename = "./Database/Customer.csv";
        

    }

    @Override
    public Customer get(int id) {
      
      String line;
      String[] lineSplit = new String[2];
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
            return new Customer(Integer.parseInt(lineSplit[0]), lineSplit[1],
                lineSplit[2], lineSplit[3], lineSplit[4]);
        } 
        else {
            return null;
        }
    }

    @Override
    public void add(Customer n) {
        int id = n.getId();
        String name = n.getfName();
        String sname = n.getlName();
        String email = n.getEmail();
        String password = n.getPassword();
        
        try {
            Customer existingCustomer = this.get(id);
            
            if(existingCustomer != null) {
                // Fix error message
                System.out.println(name + " " + sname + " already exists!");
            } else {
                bw = new BufferedWriter(new FileWriter(filename, true));
                String newLine = id + "," + name + "," + sname + "," + email + "," + password + "\n";
                bw.append(newLine);
                bw.close();
                
                System.out.println("Successfully added: " + name);
            }
        } catch (IOException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public ArrayList<Customer> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer get(String email, String password) {
      String line;
      String[] lineSplit = new String[2];
      boolean found = false;
      
        try {
            br = new BufferedReader(new FileReader(filename));
            
            while(!found && (line = br.readLine()) != null) {
                lineSplit = line.split(",");
                if(lineSplit[3].equals(email)) {
                    if(lineSplit[4].equals(password)){
                        found = true;
                    }
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
            return new Customer(Integer.parseInt(lineSplit[0]), lineSplit[1],
                lineSplit[2], lineSplit[3], lineSplit[4]);
        } 
        else {
            return null;
        }
    }
    
}
