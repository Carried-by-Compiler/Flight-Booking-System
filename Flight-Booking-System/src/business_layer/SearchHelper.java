/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import business_layer.shortestpathalgos.Dijkstra;
import business_layer.shortestpathalgos.Edge;
import business_layer.shortestpathalgos.FlightSearchStrategy;
import business_layer.shortestpathalgos.Graph;
import business_layer.shortestpathalgos.Node;
import business_layer.shortestpathalgos.Path;
import business_layer.shortestpathalgos.Yen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * This class helps in integrating the business logic and entities with
 * the shortest path algorithms.
 * 
 * @author John Rey Juele
 */
public class SearchHelper {
    
    public static final int DIJKSTRA = 1;
    public static final int YEN      = 2;
    
    private FlightSearchStrategy flightSearcher;
    
    public SearchHelper(int method) {
        
        switch(method) {
            case DIJKSTRA:
                this.flightSearcher = new Dijkstra();
                break;
            
            case YEN:
                this.flightSearcher = new Yen();
                break;
        }
    }
    
    /**
     * Convert the flights into a graph.
     * @param flights List of flights
     * @return A Graph object.
     */
    public Graph createGraph(List<Flight> flights) {
        Graph graph;
        
        Node departNode = null;
        Node arriveNode = null;
        
        // Ensures that a city is not added twice
        HashSet<String> cities = new HashSet<String>();
        
        // Chose to use a hash set to avoid any duplicate nodes.
        HashSet<Node> nodes = new HashSet<Node>();
        ArrayList<Edge> edges = new ArrayList<Edge>();
        
        for(Flight flight : flights) {
            String depart = flight.getDeparture();
            String arrive = flight.getArrival();
            
            if(!cities.contains(depart)) {
                cities.add(depart);
            }
            
            if(!cities.contains(arrive)) {
                cities.add(arrive);
            }
            
            departNode = new Node(depart);
            arriveNode = new Node(arrive);
            
            nodes.add(departNode);
            nodes.add(arriveNode);
            
            edges.add(flight);
        }
        
        graph = new Graph(new ArrayList<Node>(nodes), edges);
        
        return graph;
    }
    
    public List<Path> runAlgorithm(Graph graph, String dep, String arr) {
        List<Path> paths = new ArrayList<Path>();
        
        this.flightSearcher.resetAlgorithm();
        this.flightSearcher.setGraph(graph);
        boolean success = this.flightSearcher.execute(new Node(dep), new Node(arr));
        if(success) {
            paths = this.flightSearcher.getShortestPaths();
        } else {
            // TODO make better error handling mechanism
            System.out.println("Error while searching flights");
        }
        
        return paths;
    }
    
    /**
     * Convert a path in a graph into a list of flights
     * @param route A path in a graph.
     * @return A list of flights in that path.
     */
    public List<Flight> convertPathToFlights(Path route) {
        List<Flight> flights = new ArrayList<Flight>();
        
        List<Edge> edges = route.getEdges();
        
        for(Edge edge : edges) {
            flights.add((Flight)edge);
        }
        
        return flights;
    }
    
    /**
     * Get the total cost of a path.
     * @param route A path in a graph.
     * @return The cost.
     */
    public double getPathCost(Path route) {
        return route.getCost();
    }
}
