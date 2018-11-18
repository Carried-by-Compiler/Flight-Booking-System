/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer.shortestpathalgos;

import business_layer.Flight;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a path in a graph
 * 
 * Taken inspiration from:
 * AUTHOR:          Smock, B.
 * TITLE:           k-shortest-paths
 * DATE:            Dec. 28 2015
 * CODE VERSION:    1.0.1
 * AVAILABILITY:    https://github.com/bsmock/k-shortest-paths
 * 
 * 
 * @author John Rey Juele
 */
public class Path implements Comparable<Path> {
    private List<Edge> edges;
    private double totalCost;
    
    /**
     * Constructor to initialize a path with a list of edges and the corresponding
     * total cost.
     * @param edges list of edges in the path.
     * @param cost the total cost of the path.
     */
    public Path(List<Edge> edges, double cost) {
        this.edges = edges;
        this.totalCost = cost;
    }
    
    /**
     * Constructor to initialize a path with a list of edges. This constructor
     * will automatically calculate the total cost using the cost of each edge
     * in the list.
     * @param edges the list of edges. 
     */
    public Path(List<Edge> edges) {
        this.edges = edges;
        this.totalCost = 0.0;
        
        for(Edge edge : this.edges) {
            this.totalCost += edge.getCost();
        }
    }
    
    /**
     * Get edges within the path
     * @return list of edges in the path
     */
    public List<Edge> getEdges() {
        return edges;
    }
    
    /**
     * Get the nodes within the path
     * @return a list of nodes in the path
     */
    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<Node>();
        
        for(Edge edge : edges) {
            nodes.add(edge.getOrigin());
        }
        
        Edge lastEdge = edges.get(edges.size() - 1);
        if(lastEdge != null) {
            nodes.add(lastEdge.getDestination());
        }
        
        return nodes;
    }
    
    /**
     * Get the total cost of all the edges in the path
     * @return return the cost
     */
    public double getCost() {
        return this.totalCost;
    }
    
    /**
     * Get the length of the path
     * @return the length of the path
     */
    public int size() {
        return edges.size();
    }
    
    /**
     * Get the subpath from the starting node of this current path to the i-th
     * node if this path.
     * @param i the node at the i-th position
     * @return the subpath
     */
    public Path getPathTo(int i) {
        List<Edge> e = new ArrayList<Edge>();
        int length = this.edges.size();
        
        if (i > length)
            i = length;
        
        for(int j = 0; j < i; j++) {
            e.add((Flight)this.edges.get(j).clone());
        }
        
        return new Path(e);
    }
    
    /**
     * Join two paths together.
     * @param newPath the second path.
     */
    public void joinPath(Path newPath) {
        this.edges.addAll(newPath.getEdges());
        this.totalCost += newPath.getCost();
        /*
        if(this.size() != 0) {
            Node firstNode = newPath.getEdges().get(0).getOrigin();
            Node thisLastNode = this.edges.get(this.edges.size() - 1).getDestination();

            if(thisLastNode.equals(firstNode)) {
                this.edges.addAll(newPath.getEdges());
                this.totalCost += newPath.getCost();
            } else {
                System.out.println("PATH.java error:\t first and last nodes don't align");
            }
        }        
        */
    }
    
    /**
     * Checks if the this path is equal to the passed in path.
     * @param p2 the second path.
     * @return returns true if the paths are equal.
     */
    public boolean equals(Path p2) {
        boolean ok = true;
        if(p2 == null) {
            ok = false;
        }
        
        List<Edge> e1 = this.getEdges();
        List<Edge> e2 = p2.getEdges();
        
        int numEdges1 = edges.size();
        int numEdges2 = e2.size();
        
        if(numEdges1 != numEdges2)
            ok = false;
        
        for(int i = 0; i < this.edges.size() && ok; i++) {
            Edge edge = e1.get(i);
            Edge otherEdge = e2.get(i);
            
            if(!edge.equals(otherEdge)) {
                ok = false;
            }
        }
        
        /*
        for(int i = 0; i < numEdges1; i++) {
            Edge edge1 = edges.get(i);
            Edge edge2 = e2.get(i);
            if(!edge1.getOrigin().equals(edge2.getOrigin())) {
                return false;
            }
            if(!edge1.getDestination().equals(edge2.getDestination())) {
                return false;
            }
        }
        */
        return ok;
    }

    @Override
    public int compareTo(Path o) {
        double path2Cost = o.getCost();
        if(this.totalCost == path2Cost) {
            return 0;
        }
        if(totalCost > path2Cost)
            return 1;
        
        return -1;
    }
    
    public Path clone() {
        ArrayList<Edge> e = new ArrayList<Edge>();
        
        for(Edge<Flight> edge : this.edges) {
            e.add(edge.clone());
        }
        
        return new Path(e, this.totalCost);
    }
    
    /**
     * For testing purposes only!
     */
    public void printPath() {
        for(Edge e : this.edges) {
            System.out.print(e.getOrigin().getName() + "->");
        }
        System.out.println(this.getNodes().get(this.getNodes().size() - 1).getName() + "\n");
    }
}
