/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import DB_Layer.Dao;
import DB_Layer.DaoFactory;
import business_layer.shortestpathalgos.Graph;
import business_layer.shortestpathalgos.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ui.controllers.FlightObserver;

/**
 * Flight Manager
 */
public class FlightsManager implements FlightSubject {
    public static final int DEPART = 0;
    public static final int RETURN = 1;
    
    private Dao<Flight> dao;
    private List<FlightObserver> observers;
    private List<Flight> allFlights;
    
    private SearchHelper searchHelper;
    
    public FlightsManager() {
        this.dao = DaoFactory.getDao(DaoFactory.FLIGHT);
        this.observers = new ArrayList<FlightObserver>();
        
        this.searchHelper = new SearchHelper(SearchHelper.YEN);
        this.allFlights = this.dao.getAll();
    }
    
    /**
     * Adds and stores a flight.
     * @param id The flight ID.
     * @param aid The airline ID that is supporting the flight.
     * @param dep The departure city.
     * @param arr The destination city.
     * @param dTime The scheduled departure of the flight.
     * @param aTime The scheduled arrival of the flight.
     * @param cost The cost of the flight.
     */
    public void addFlight(int id, int aid, String dep, String arr, 
        LocalDateTime dTime, LocalDateTime aTime, double cost) {
        
        Flight f = new Flight(id, aid, dep, arr, dTime, aTime, cost);
        
        dao.add(f);
    }
    
    /**
     * Searches for direct flights. This method only looks for one way flights between 
     * the two cities provided.
     * @param departure The departure city.
     * @param arrival The arriving city.
     * @param dDate The departing date of flights to search for.
     * @return A list of flights in accordance to the search criteria of the user.
     * @throws NoFlightsFoundException If no flights were found. 
     */
    public ArrayList<Flight> getDirectFlights(String departure, String arrival, 
            LocalDate dDate) throws NoFlightsFoundException {
        List<Flight>  appropriateFlights = getFlightsAfterDate(dDate);
        ArrayList<Flight> foundFlights = new ArrayList<Flight>();
        ArrayList<String> data = new ArrayList<String>();
        boolean found = false;
        
        String airlines, numOfStops, times, cities, cost;
        
        for(Flight f : appropriateFlights) {
            
            String flightDeparture = f.getDeparture();
            String flightArrival = f.getArrival();
            
            if(flightDeparture.equals(departure) && flightArrival.equals(arrival)) {
                found = true;
                foundFlights.add(f);
            }
        }
        
        if(!found) {
            throw new NoFlightsFoundException("No flights to " + departure + " was found.");
        } else {
            Collections.sort(foundFlights); // Sorts by flight cost.
            for(Flight flight : foundFlights) {
                airlines = getAssociatedAirlines(flight);
                numOfStops = "0"; 
                times = getScheduleOfEachFlight(flight);
                cities = departure + "," + arrival;
                cost = String.valueOf(flight.getCost());
                
                data.add(airlines);
                data.add(numOfStops);
                data.add(times);
                data.add(cities);
                data.add(String.valueOf(flight.getCost()));
                
                this.notifyObservers(data, DEPART);
            }
        }
        
        return foundFlights;
    }
    
    
    public ArrayList<Flight> getDirectFlights(String departure, String arrival, 
            LocalDate dDate, LocalDate rDate) throws NoFlightsFoundException {
        ArrayList<Flight> flights = dao.getAll();
        ArrayList<Flight> foundFlights = new ArrayList<Flight>();
        
        /*
        for(Flight f : flights) {
            
            if(f.getDeparture().equals(criteria[0]) && f.getArrival().equals(criteria[1])) {
                if(f.compareTo(LocalDate.parse(criteria[2])) == 0) {
                    foundFlights.add(f);
                }
            }
            
        }
        */
        return foundFlights;
    } 
    
    /**
     * Obtain flights that is in accordance to user's input where connecting flights
     * can occur. It also is used to provide all its observers of the flights found.
     * 
     * @param departure Name of origin city,
     * @param arrival Name of destination city.
     * @param date The departure date.
     */
    public void getFlightsMultipleStop(String departure, String arrival, LocalDate date) throws NoFlightsFoundException {
        // TODO, limit flight search to 1 or 2 days after departure date.
        List<Flight> appropriateFlights = getFlightsAfterDate(date);
        List<Path> routes = new ArrayList<Path>();
        ArrayList<String> data = new ArrayList<String>();
        
        Graph graph = this.searchHelper.createGraph(appropriateFlights);
        boolean success = this.searchHelper.runAlgorithm(graph, departure, arrival);
        if(success) {
            routes = this.searchHelper.getResults();
        } else {
            throw new NoFlightsFoundException("No flights to " + departure + " was found.");
        }
        
        removeInvalidFlights(routes);
       
        for(Path route : routes) {
            data = new ArrayList<String>();
            route.printPath();
            String airlines = getAssociatedAirlines(route);
            String numOfStops = getNumberOfStops(route);
            String times = getScheduleOfEachFlight(route);
            String cities = getCitiesInRoute(route);
            
            data.add(airlines);
            data.add(numOfStops);
            data.add(times);
            data.add(cities);
            data.add(String.valueOf(route.getCost()));
            
            this.notifyObservers(data, DEPART);
        }
    }
    
    /**
     * This is an overloaded method that searches for both departing and returning
     * flights that are in accordance to user's input where connecting flights can
     * occur. It also is used to provide all its observers of the flights found.
     * 
     * @param departure Name of origin city.
     * @param arrival Name of destination city.
     * @param dDate The departure date.
     * @param rDate The return date.
     * 
     * @throws NoFlightsFoundException Thrown when no flights were found. 
     */
    public void getFlightsMultipleStop(String departure, String arrival, 
            LocalDate dDate, LocalDate rDate) throws NoFlightsFoundException {
        // TODO, limit flight search to 1 or 2 days after departure date.
        List<Flight> flightsDepart = getFlightsBetweenDates(dDate, rDate);
        List<Flight> flightsReturn = getFlightsAfterDate(rDate);
        List<Path> departingRoutes = new ArrayList<Path>();
        List<Path> returningRoutes = new ArrayList<Path>();
        ArrayList<String> data = new ArrayList<String>();
        
        String airlines, numOfStops, times, cities;
        boolean success;
        
        Graph graph = this.searchHelper.createGraph(flightsDepart);
        success = this.searchHelper.runAlgorithm(graph, departure, arrival);
        if(success) {
            departingRoutes = this.searchHelper.getResults();
        } else {
            throw new NoFlightsFoundException("Flights to " + arrival + " not found.");
        }
        
        graph = this.searchHelper.createGraph(flightsReturn);
        success = this.searchHelper.runAlgorithm(graph, arrival, departure);
        if(success) {
            returningRoutes = this.searchHelper.getResults();
        } else {
            throw new NoFlightsFoundException("Flights to " + departure + " not found.");
        }
        
        removeInvalidFlights(departingRoutes);
        removeInvalidFlights(returningRoutes);
        
        for(Path route : departingRoutes) {
            data = new ArrayList<String>();
            
            airlines = getAssociatedAirlines(route);
            numOfStops = getNumberOfStops(route);
            times = getScheduleOfEachFlight(route);
            cities = getCitiesInRoute(route);
            
            data.add(airlines);
            data.add(numOfStops);
            data.add(times);
            data.add(cities);
            data.add(String.valueOf(route.getCost()));
            
            this.notifyObservers(data, DEPART);
        } 
        
        for(Path route : returningRoutes) {
            data = new ArrayList<String>();
            
            airlines = getAssociatedAirlines(route);
            numOfStops = getNumberOfStops(route);
            times = getScheduleOfEachFlight(route);
            cities = getCitiesInRoute(route);
            
            data.add(airlines);
            data.add(numOfStops);
            data.add(times);
            data.add(cities);
            data.add(String.valueOf(route.getCost()));
            
            this.notifyObservers(data, RETURN);
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
    
    /**
     * Gets all the airline names involved in the flight path.
     * @param route Path object representing a flight path.
     * @return A comma delimited string of all the airlines involved.
     */
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
    
    /**
     * Get the airline name providing the given flight.
     * @param flight The flight object.
     * @return The airline name.
     */
    private String getAssociatedAirlines(Flight flight) {
        AirlineManager manager = new AirlineManager();
        
        int airlineID = flight.getAirLineID();
        String airlineName = manager.searchAirline(airlineID);
        
        return airlineName;
    }
    
    private String getNumberOfStops(Path route) {
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
    
    private String getScheduleOfEachFlight(Flight flight) {
        LocalDateTime dTime;
        LocalDateTime aTime;    
        
        String returnVal = "";
        
        dTime = flight.getDepTime();
        aTime = flight.getArrTime();

        returnVal += dTime.toString() + "/" + aTime.toString();
        
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
    
    /**
     * Returns a list of flights that are scheduled after the given date.
     * @param date The date.
     * @return A list of flights.
     */
    private List<Flight> getFlightsAfterDate(LocalDate date) {
        ArrayList<Flight> appropriateFlights = new ArrayList<Flight>();
        
        for(Flight flight : this.allFlights) {
            if(isAfterDate(flight, date)) {
                appropriateFlights.add(flight);
            }
        }
        
        return appropriateFlights;
    }
    
    private List<Flight> getFlightsBetweenDates(LocalDate dDate, LocalDate rDate) {
        ArrayList<Flight> appropriateFlights = new ArrayList<Flight>();
        
        for(Flight flight : this.allFlights) {
            if(isAfterDate(flight, dDate) && isBeforeDate(flight, rDate)) {
                appropriateFlights.add(flight);
            }
        }
        
        return appropriateFlights;
    }
    
    /**
     * Checks if a flight is scheduled after or on a particular date.
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
    
    /**
     * Checks if a flight is before a particular date.
     * @param flight The flight object.
     * @param date The date to compare.
     * @return Returns true if a flight is scheduled before or at the given date.
     */
    public boolean isBeforeDate(Flight flight, LocalDate date) {
        LocalDate flightDate = flight.getDepTime().toLocalDate();
        
        if(flightDate.isBefore(date)) {
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
    public void notifyObservers(ArrayList<String> data, int type) {
        for(FlightObserver observer : this.observers) {
            observer.update(data, type);
        }
    }
}
