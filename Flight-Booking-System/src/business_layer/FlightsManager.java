/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import DB_Layer.Dao;
import DB_Layer.DaoFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author John Rey Juele
 */
public class FlightsManager {
    private Dao<Flight> dao;
    
    public FlightsManager() {
        this.dao = DaoFactory.getDao(DaoFactory.FLIGHT);
    }
    
    public void addFlight(int id, int aid, String dep, String arr, 
            LocalDateTime dTime, LocalDateTime aTime) {
        
        Flight f = new Flight(id, aid, dep, arr, dTime, aTime);
        
        dao.add(f);
    }
    
    public ArrayList<Flight> getFlightsOneWay(String[] criteria) {
        ArrayList<Flight> flights = dao.getAll();
        ArrayList<Flight> foundFlights = new ArrayList<Flight>();
        
        for(Flight f : flights) {
            
            if(f.getDeparture().equals(criteria[0]) && f.getArrival().equals(criteria[1])) {
                if(f.compareTo(LocalDate.parse(criteria[2])) == 0) {
                    foundFlights.add(f);
                }
            }
            
        }
        
        return foundFlights;
    }
}
