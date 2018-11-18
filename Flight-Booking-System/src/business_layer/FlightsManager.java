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
import business_layer.shortestpathalgos.Path;
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
        
        this.searchHelper = new SearchHelper(SearchHelper.YEN);
    }
    
    public void addFlight(int id, int aid, String dep, String arr, 
        LocalDateTime dTime, LocalDateTime aTime, double cost) {
        
        Flight f = new Flight(id, aid, dep, arr, dTime, aTime, cost);
        
        dao.add(f);
    }
    
    /**
     * Obtain flights that is in accordance to user's input where connecting flights
     * can occur. It also is used to provide all its observers of the flights found.
     * 
     * @param departure Name of origin city,
     * @param arrival Name of destination city.
     * @param date The departure date.
     */
    public void getFlightsMultipleStop(String departure, String arrival, LocalDate date) {
        List<Flight> flights = this.dao.getAll();
        List<Flight> appropriateFlights = getFlightsAfterDate(flights, date);
        ArrayList<String> data = new ArrayList<String>();
        
        Graph graph = this.searchHelper.createGraph(appropriateFlights);
        List<Path> routes = this.searchHelper.runAlgorithm(graph, departure, arrival);
        
        removeInvalidFlights(routes);
       
        for(Path route : routes) {
            data = new ArrayList<String>();
            route.printPath();
            String airlines = getAssociatedAirlines(route);
            String numOfStops = getNumberOfStops(route, arrival);
            String times = getScheduleOfEachFlight(route);
            String cities = getCitiesInRoute(route);
            
            data.add(airlines);
            data.add(numOfStops);
            data.add(times);
            data.add(cities);
            data.add(String.valueOf(route.getCost()));
            
            this.notifyObservers(data);
        }
    }
    
    private void removeInvalidFlights(List<Path> routes) {
        List<Flight> flights;
        List<Path> removedPaths = new ArrayList<Path>();
        
        LocalDateTime startingTime, departingTime;
        
        for(Path path : routes) {
            flights = this.searchHelper.convertPathToFlights(path);
            startingTime = flights.get(0).getDepTime();
            for(int i = 1; i < flights.size(); i++) {
                departingTime = flights.get(i).getDepTime();
                if(departingTime.isBefore(startingTime)) {
                    removedPaths.add(path);
                    break;
                }
            }
        }
        
        if(!removedPaths.isEmpty())
            routes.removeAll(removedPaths);
    }
    
    private String getAssociatedAirlines(Path route) {
        AirlineManager manager = new AirlineManager();
        String returnVal = "";
        
        List<Flight> flights = this.searchHelper.convertPathToFlights(route);
        
        for(Flight flight : flights) {
            
            int aID = flight.getAirLineID();
            String airline = manager.searchAirline(aID);
            returnVal += airline + ",";
        }
        
        return returnVal;
    }
    
    private String getNumberOfStops(Path route, String arrival) {
        int stopCounter = 0;
        
        List<Flight> flights = this.searchHelper.convertPathToFlights(route);
        
        stopCounter = flights.size() - 1;
        
        return String.valueOf(stopCounter);
    }
    
    private String getScheduleOfEachFlight(Path route) {
        List<Flight> flights = this.searchHelper.convertPathToFlights(route);
        LocalDateTime dTime;
        LocalDateTime aTime;    
        
        String returnVal = "";
        
        for(Flight flight : flights) {
            dTime = flight.getDepTime();
            aTime = flight.getArrTime();
            
            returnVal += dTime.toString() + "/" + aTime.toString() + ",";
        }
        
        return returnVal;
    }
    
    public String getCitiesInRoute(Path route) {
        List<Flight> flights = this.searchHelper.convertPathToFlights(route);
        String returnVal = "";
        
        for(Flight flight : flights) {
            returnVal += flight.getDeparture() + ",";
        }
        returnVal += flights.get(flights.size() - 1).getArrival();
        
        return returnVal;
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
    public void notifyObservers(ArrayList<String> data) {
        for(FlightObserver observer : this.observers) {
            observer.update(data);
        }
    }
}
