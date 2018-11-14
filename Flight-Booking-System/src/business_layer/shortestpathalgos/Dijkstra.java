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
public class Dijkstra {
    private List<Node> nodes;
    private List<Edge> edges;
    
    private Node end;
    
    private Set<Node> visited;
    private Set<Node> unvisited;   
    
    private Map<Node, Double> distances;
    private Map<Node, Node> predecessors;
    
    public Dijkstra(Graph graph, Node end) {
        this.nodes = new ArrayList<Node>(graph.getNodes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
        
        this.end = end;
        
        // Initialize the unvisited set
        this.unvisited = new HashSet<Node>(graph.getNodes());
        // Initialize the visited set as empty
        this.visited = new HashSet<Node>();
        
        // Initialize the maps
        this.distances =  new HashMap<Node, Double>();
        this.predecessors = new HashMap<Node, Node>();
    }
    
    /**
     * This method executes the algorithm. As this is running,
     * it is filling the maps with the relevant information needed
     * to find the shortest path as well as the nodes comprising this
     * path.
     * 
     * @param source the starting node of the algorithm 
     */
    public boolean execute(Node source) {
        // add boolean if executed
        boolean success = true;
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
     * Get the neighboring nodes of the passed in node.
     * @param currentNode the node of which we want to find the neighboring nodes to.
     * @return returns a list of nodes that is neighboring the passed in node.
     */
    private List<Node> getNeighbours(Node currentNode) {
        List<Node> neighbours = new ArrayList<Node>();
        
        for (Edge edge : this.edges) {
            if(edge.getOrigin().equals(currentNode) && !this.visited.contains(edge.getDestination()))
                neighbours.add(edge.getDestination());
        }
        return neighbours;
    }
    
    /**
     * Calculates and updates the cost to the next node. It also updates
     * the predecessors map
     * @param current the current node
     * @param next the neighbor node
     */
    private void calculateCost(Node current, Node next) {
        if(!this.distances.containsKey(next))
            this.distances.put(next, Double.MAX_VALUE);
        
        Double costOfCurrent = this.distances.get(current);
        Double costOfEdge = getEdgeCost(current, next);
        Double costOfNext = this.distances.get(next);
        Double cost2Next = costOfCurrent + costOfEdge;
        
        if(cost2Next < costOfNext) {
            this.distances.replace(next, cost2Next);
            updatePredecessor(current, next);
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
     * Get the shortest path
     * @param target the end node
     * @return the shortest path
     */
    public Path getShortestPath() {
        
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
            Edge link = getEdge(current, previous);
            edges.add(link);
            
            current = previous;
            previous = this.predecessors.get(current);
            
        }
        Collections.reverse(edges);
        
        return new Path(edges, pathCost);
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

