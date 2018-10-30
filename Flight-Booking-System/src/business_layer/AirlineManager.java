/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import DB_Layer.Dao;
import DB_Layer.DaoFactory;

/**
 *
 * @author John Rey Juele
 */
public class AirlineManager {
    private Dao<Airline> dao;
    
    public AirlineManager() {
        this.dao = DaoFactory.getDao(DaoFactory.AIRLINE);
    }
    
    public void addAirline(int id, String name, String password) {
        // TODO: add information validation before passing to DAO
        dao.add(new Airline(id, name, password));
    }
    
    public String searchAirline(int id) {
        Airline airline = dao.get(id);
        
        if(airline == null)
            return "Airline does not exist";
        else
            return airline.toString();
    }
}
