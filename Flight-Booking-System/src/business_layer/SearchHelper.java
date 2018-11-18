/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import business_layer.shortestpathalgos.Edge;
import business_layer.shortestpathalgos.FlightSearchStrategy;
import business_layer.shortestpathalgos.Graph;
import business_layer.shortestpathalgos.Node;
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
    
    private FlightSearchStrategy flightSearcher;
    
    public SearchHelper() {
        this.flightSearcher = new Yen();
    }
    
    public Graph createGraph(List<Flight> flights) {
        Graph graph;
        
        Node departNode = null;
        Node arriveNode = null;
        
        HashMap<String, Integer> cities = new HashMap<String, Integer>();
        
        HashSet<Node> nodes = new HashSet<Node>();
        ArrayList<Edge> edges = new ArrayList<Edge>();
        
        for(Flight flight : flights) {
            String depart = flight.getDeparture();
            String arrive = flight.getArrival();
            
            if(!cities.containsKey(depart)) {
                cities.put(depart, cities.size());
            }
            
            if(!cities.containsKey(arrive)) {
                cities.put(arrive, cities.size());
            }
            
            departNode = new Node(cities.get(depart), depart);
            arriveNode = new Node(cities.get(arrive), arrive);
            
            nodes.add(departNode);
            nodes.add(arriveNode);
            
            Edge edge = new Edge(edges.size(), departNode, arriveNode, flight.getCost());
            edges.add(edge);
        }
        
        graph = new Graph(new ArrayList<Node>(nodes), edges);
        
        return graph;
    }
    
    public void runAlgorithm(Graph graph) {
        
    }
}
