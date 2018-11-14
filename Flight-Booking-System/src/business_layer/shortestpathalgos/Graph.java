/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer.shortestpathalgos;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Graph entity.
 *
 * @author John Rey Juele
 */
public class Graph {

    private List<Node> nodes;
    private List<Edge> edges;
    
    /**
     * Initialize the nodes and edges within the graph
     * @param nodes a list of nodes
     * @param edges a list of edges
     */
    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    /**
     * @return a list of nodes in the graph object 
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * @return a list of edges in the graph object 
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Removes the edge connect the two nodes.
     * @param n1 node 1
     * @param n2 node 2
     * @return returns the removed edge.
     */
    public Edge removeEdge(Node n1, Node n2) {
        double cost = 0.0;
        Edge e = null;
        if(nodes.contains(n1) && nodes.contains(n2)) {
            for(Edge edge : this.edges) {
                if(edge.getOrigin().equals(n1) && edge.getDestination().equals(n2)) {
                    
                    cost = edge.getCost();
                    Node origin = edge.getOrigin();
                    Node destination = edge.getDestination();
                    int id = edge.getId();
                    e = new Edge(id, origin, destination, cost);
                    this.edges.remove(edge);
                    
                    break;
                }
            }
        }
        
        return e;
    } 
    
    /**
     * Prints out all edges in the graph as well as the nodes that each of these
     * edges connect to. 
     */
    public void printGraph() {
        for(Edge e : this.edges) {
            System.out.print(e.getOrigin().getName() + "->" + e.getDestination().getName() + " ");
        }
        System.out.println();
    }
    
    /**
     * Removes a node in the graph. This also removes all edges connected to this node.
     * @param node The node to remove.
     */
    public void removeNode(Node node) {
        removeEdgesEnteringNode(node); // Remove edges leading to the node
        removeEdgesLeavingNode(node); // Removes the edges that exits the node
        
        this.nodes.remove(node);
    }
    
    private void removeEdgesEnteringNode(Node node) {
        List<Edge> edges2Remove = new ArrayList<Edge>();
        
        for(Edge edge : this.edges) {
            if(node.equals(edge.getDestination())) {
                edges2Remove.add(edge);
            }
        }
        
        this.edges.removeAll(edges2Remove);
    }
    
    private void removeEdgesLeavingNode(Node node) {
        List<Edge> edges2Remove = new ArrayList<Edge>();
        
        for(Edge edge : this.edges) {
            if(node.equals(edge.getOrigin())) {
                edges2Remove.add(edge);
            }
        }
        
        this.edges.removeAll(edges2Remove);
    }
    
    /**
     * Creates a deep copy of the graph object.
     * @return graph copy.
     */
    public Graph clone() {
        List<Node> nodeCopy = new ArrayList<Node>();
        List<Edge> edgeCopy = new ArrayList<Edge>();
        
        for(Node node : this.nodes) {
            nodeCopy.add(node.clone());
        }
        
        for(Edge edge : this.edges) {
            edgeCopy.add(edge.clone());
        }
        
        return new Graph(nodeCopy, edgeCopy);
    }
}
