/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer.shortestpathalgos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implements Dijkstra's Algorithm to help with flight searching
 * 
 * Reference:
 * Title: Dijkstra's shortest path algorithm in Java - Tutorial
 * Author: Vogel L.
 * Date: 2018
 * Code Version: 1.2
 * Availability: http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
 * 
 * https://github.com/bsmock/k-shortest-paths
 * 
 * @author John Rey Juele
 */
public class Dijkstra implements FlightSearchStrategy {
    private List<Node> nodes;
    private List<Edge> edges;
    
    private Node end;
    
    private Set<Node> visited;
    private Set<Node> unvisited;   
    
    private Map<Node, Double> distances;
    private Map<Node, Node> predecessors;
    private Map<Node, Edge> previousEdges;
    
    public Dijkstra() {
        /*
        this.nodes = new ArrayList<Node>(graph.getNodes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        
        this.end = end;
        */
        // Initialize the unvisited set
        //this.unvisited = new HashSet<Node>(graph.getNodes());
        // Initialize the visited set as empty
        this.visited = new HashSet<Node>();
        
        // Initialize the maps
        this.distances =  new HashMap<Node, Double>();
        this.predecessors = new HashMap<Node, Node>();
        this.previousEdges = new HashMap<Node, Edge>();
    }
    
    @Override
    public void resetAlgorithm() {
        // Initialize the visited set as empty
        this.visited = new HashSet<Node>();
        
        // Initialize the maps
        this.distances =  new HashMap<Node, Double>();
        this.predecessors = new HashMap<Node, Node>();
        this.previousEdges = new HashMap<Node, Edge>();
    }
    /**
     * Sets the graph in place for the algorithm to work on.
     * @param graph The graph object.
     */
    public void setGraph(Graph graph) {
        this.nodes = new ArrayList<Node>(graph.getNodes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        
        this.unvisited = new HashSet<Node>(graph.getNodes());
    }
    
    /**
     * This method executes the algorithm. As this is running,
     * it is filling the maps with the relevant information needed
     * to find the shortest path as well as the nodes comprising this
     * path.
     * 
     * @param start the starting node of the algorithm .
     * @param end the end node of the algorithm.
     * @return Returns true if successful. Otherwise, false.
     */
    @Override
    public boolean execute(Node start, Node end) {
        
        this.end = end;
        
        boolean success = true;
        Node currentNode = null;
        List<Edge> neighboringEdges = new ArrayList<Edge>();
        
        this.distances.put(start, 0.0);
        
        while(!this.unvisited.isEmpty()) {
            currentNode = getNextCurrent();
            neighboringEdges = getNeighbors(currentNode);
            
            // If the currentNode is the spurnode and it has no neigbors, stop
            // the algorithm.
            if((start.equals(currentNode) && neighboringEdges.isEmpty()) || currentNode == null) {
                success = false;
                break;
            } else {
                for(Edge edge : neighboringEdges) {
                    calculateCost(edge);
                }
                this.visited.add(currentNode);
                this.unvisited.remove(currentNode);
            }
        }
        
        
        
        
        
        /*
        this.distances.put(source, 0.0);
        Node currentNode = source;
        int counter = 0;
        
        while(!this.unvisited.isEmpty() && canContinue()) {
            currentNode = getNextCurrent();
            List<Node> neighbours = getNeighbours(currentNode);
            
            if(outward(currentNode)) {
                if(neighbours.size() != 0) {
                    for(Node neighbour : neighbours) {
                        calculateCost(currentNode, neighbour);
                    }
                }
            } else {
                counter++;
            }
            
            
            
            this.visited.add(currentNode);
            this.unvisited.remove(currentNode);
            if(counter == 5) {
                success = false;
                break;
            }
        }
        */
        return success;
    }
    
    private boolean outward(Node node) {
        boolean outward = false;
        for(Edge e : this.edges) {
            if(e.getOrigin().equals(node)) {
                outward = true;
            }
        }
        return outward;
    }
    
    private boolean canContinue() {
        boolean progress = false;
        boolean hasDistanceValue = false;
        /*
        Check if the target node is unvisited.
        If not:
           - continue
        Else:
           - check if there are other nodes who's value is not infinity
        
        */
        if(!this.visited.contains(end)) {
            progress = true;
        } else {
            for(Node node : unvisited) {
                if(this.distances.get(node) != null) {
                    hasDistanceValue = true;
                    break;
                }
            }
            
            if(hasDistanceValue) {
                progress = true;
            }
        }
        
        return progress;
    }
    
    /**
     * Find the next unvisited Node with the smallest known distance 
     * @return the node with the smallest known distance
     */
    private Node getNextCurrent() {
        double minimum = Double.MAX_VALUE;
        Node minimumNode = null;
        Double costOfNode;
        
        for(Node node : this.unvisited) {
            costOfNode = this.distances.get(node);
            if(costOfNode != null && costOfNode <= minimum) {
                minimum = costOfNode;
                minimumNode = node;
            }
        }
        return minimumNode;
    }
    
    /**
     * Get the edges leaving the parameter node. 
     * @param currentNode the node of which we want to find the neighboring nodes to.
     * @return A list of edges that leaves the current node.
     */
    private ArrayList<Edge> getNeighbors(Node currentNode) {
        ArrayList<Edge> neighbours = new ArrayList<Edge>();
        
        for (Edge edge : this.edges) {
            if(edge.getOrigin().equals(currentNode) && !this.visited.contains(edge.getDestination()))
                neighbours.add(edge);
        }
        return neighbours;
    }
    
    /**
     * Calculates and updates the cost to the next node. It also updates
     * the predecessors map
     * @param current the current node
     * @param next the neighbor node
     */
    private void calculateCost(Edge edge) {
        
        Node from = edge.getOrigin();
        Node to = edge.getDestination();
        Double edgeCost = edge.getCost();
        int edgeID = edge.getId();
        
        // If the cost to the next node is still unknown, initialize its cost
        // to infinity (in this case, to the max value of a double.
        if(!this.distances.containsKey(to))
            this.distances.put(to, Double.MAX_VALUE);
        
        Double costOfCurrent = this.distances.get(from);
        Double costOfNext = this.distances.get(to);
        Double cost2Next = costOfCurrent + edgeCost;
        
        if(cost2Next < costOfNext) {
            this.distances.replace(to, cost2Next);
            updatePredecessor(from, to);
            updatePreviousEdge(to, edge);
        }
    }
    
    /**
     * Get the cost of the edge between two nodes
     * @param current the current node
     * @param next the neighbor node
     * @return returns the cost of the edge
     */
    private double getEdgeCost(Node current, Node next) {
        double cost = 0.0;
        for(Edge edge : this.edges) {
            if(edge.getOrigin().equals(current) && edge.getDestination().equals(next)) {
                cost = edge.getCost();
            }
        }
        return cost;
    }
    
    /**
     * Update the next node to have its predecessor be the current node
     * @param current the current node
     * @param next  the neighbor node
     */
    private void updatePredecessor(Node current, Node next) {
        this.predecessors.put(next, current);
    }
    
    /**
     * Keep track of what edge was used to get to that node.
     * @param next
     * @param edge 
     */
    private void updatePreviousEdge(Node next, Edge edge) {
        this.previousEdges.put(next, edge);
    }
    
    /**
     * Get the shortest path.
     * @return the shortest path
     */
    @Override
    public List<Path> getShortestPaths() {
        
        double pathCost =  this.distances.get(end);
        List<Edge> edges = new ArrayList<Edge>();
        
        Node current = end;
        Node previous = this.predecessors.get(current);
        
        if(this.predecessors.get(current) == null) {
            System.out.println("Node: " + end.getName() + " does not exist");
            return null;
        }
        
        while(previous != null) {
            previous = this.predecessors.get(current);
            Edge link = this.previousEdges.get(current);
            edges.add(link);
            
            current = previous;
            previous = this.predecessors.get(current);
            
        }
        Collections.reverse(edges);
        
        ArrayList<Path> path = new ArrayList<Path>();
        path.add(new Path(edges, pathCost));
        
        return path;
    }
    
    /**
     * Get the edge that connects two nodes.
     * @param current the current node
     * @param previous the predecessor node
     * @return the edge connecting the parameter nodes.
     */
    private Edge getEdge(Node current, Node previous) {
        Edge link = null;
        
        for(Edge edge : edges) {
            if(edge.getDestination().equals(current) && edge.getOrigin().equals(previous)) {
                link = edge;
                break;
            }
        }
        return link;
    }
}

