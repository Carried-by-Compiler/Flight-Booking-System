package business_layer.shortestpathalgos;

import business_layer.Flight;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Graph entity
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
     * @param edge2Remove The edge object to remove from the graph.
     * @return returns the removed edge.
     */
    public Edge removeEdge(Edge edge2Remove) {
        double cost = 0.0;
        Edge e = null;
        
        for(Edge<Flight> edge : this.edges) {
            if(edge.equals(edge2Remove)) {
                cost = edge.getCost();
                Node origin = edge.getOrigin();
                Node destination = edge.getDestination();
                int id = edge.getId();
                
                //e = new Edge(id, origin, destination, cost);
                e = edge.clone();
                this.edges.remove(edge);
                
                break;
            }
        }
        /*
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
        */
        return e;
    } 
    
    public void printGraph() {
        for(Edge e : this.edges) {
            System.out.print(e.getOrigin().getName() + "->" + e.getDestination().getName() + " ");
        }
        System.out.println();
    }
    
    public void removeNode(Node node) {
        removeEdgesEnteringNode(node);
        removeEdgesLeavingNode(node);
        
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
     * Create a copy of the graph.
     * @return graph copy.
     */
    public Graph clone() {
        List<Node> nodeCopy = new ArrayList<Node>();
        List<Edge> edgeCopy = new ArrayList<Edge>();
        
        for(Node node : this.nodes) {
            nodeCopy.add(node.clone());
        }
        
        for(Edge<Flight> edge : this.edges) {
            edgeCopy.add(edge.clone());
        }
        
        return new Graph(nodeCopy, edgeCopy);
    }
}
