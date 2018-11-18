/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import DB_Layer.Dao;
import DB_Layer.DaoFactory;
import business_layer.shortestpathalgos.Edge;
import business_layer.shortestpathalgos.FlightSearchStrategy;
import business_layer.shortestpathalgos.Graph;
import business_layer.shortestpathalgos.Node;
import business_layer.shortestpathalgos.Yen;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ui.FlightObserver;

/**
 *
 * @author John Rey Juele
 */
public class FlightsManager implements FlightSubject {
    private Dao<Flight> dao;
    private List<FlightObserver> observers;
    
    private SearchHelper searchHelper;
    
    public FlightsManager() {
        this.dao = DaoFactory.getDao(DaoFactory.FLIGHT);
        this.observers = new ArrayList<FlightObserver>();
        
        this.searchHelper = new SearchHelper();
    }
    
    public void addFlight(int id, int aid, String dep, String arr, 
        LocalDateTime dTime, LocalDateTime aTime, double cost) {
        
        Flight f = new Flight(id, aid, dep, arr, dTime, aTime, cost);
        
        dao.add(f);
    }
    
    /**
     * Obtain flights that is in accordance to user's input where connecting flights
     * can occur.
     * @param departure Name of origin city,
     * @param arrival Name of destination city.
     * @param date The departure date.
     */
    public void getFlightsMultipleStop(String departure, String arrival, LocalDate date) {
        List<Flight> flights = this.dao.getAll();
        
        List<Flight> appropriateFlights = getFlightsAfterDate(flights, date);
        
        Graph graph = this.searchHelper.createGraph(appropriateFlights);
        this.searchHelper.runAlgorithm(graph);
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
    
    public List<Flight> getFlightsAfterDate(List<Flight> flights, LocalDate date) {
        ArrayList<Flight> appropriateFlights = new ArrayList<Flight>();
        
        for(Flight flight : flights) {
            if(isAfterDate(flight, date)) {
                appropriateFlights.add(flight);
            }
        }
        
        return appropriateFlights;
    }
    
    /**
     * Checks if a flight is after a particular date.
     * @param flight The flight object
     * @param date The date.
     * @return Returns true if a flight is scheduled after or at the given date.
     */
    public boolean isAfterDate(Flight flight, LocalDate date) {
        LocalDate flightDate = flight.getDepTime().toLocalDate();
        
        if(flightDate.isAfter(date) || flightDate.isEqual(date)) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean correctDeparture(Flight flight, String departingCity) {
        if(flight.getDeparture().equals(departingCity)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void register(FlightObserver o) {
        this.observers.add(o);
    }

    @Override
    public void unregister(FlightObserver o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers(Flight flight) {
        for(FlightObserver observer : this.observers) {
            observer.update(flight);
        }
    }
}
