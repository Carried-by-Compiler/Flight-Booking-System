/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Layer;

import business_layer.Flight;

/**
 *
 * @author John Rey Juele
 */
public class FlightsDAO implements Dao<Flight>{
    
    private String filename;
    
    public FlightsDAO() {
        this.filename = "./Database/Flights.csv";
    }

    @Override
    public Flight get(int id) {
        
    }

    @Override
    public void add(Flight n) {
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
    
}
