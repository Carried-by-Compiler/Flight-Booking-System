package business_layer.shortestpathalgos;

/**
 * Represents an edge entity
 * @author John Rey Juele
 */
public class Edge {
    private int id;
    private Node origin;
    private Node destination;
    private double cost;
    
    /**
     * Initializes an edge object
     * @param id the identifier of the edge
     * @param origin the initial node
     * @param destination the node at the other end of the "origin" node
     * @param cost the weight of the edge
     */
    public Edge(int id, Node origin, Node destination, double cost) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
    }

    /**
     * 
     * @return returns the id of the edge 
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @return returns the origin node 
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * 
     * @return returns the destination node 
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * 
     * @return returns the weight of the edge 
     */
    public double getCost() {
        return cost;
    }
    
    /**
     * Create a deep copy of the current edge object
     * @return a deep clone
     */
    public Edge clone() {
        Node newOrigin = this.origin.clone();
        Node newDestination = this.destination.clone();
        
        return new Edge(id, newOrigin, newDestination, cost);
    }

    /**
     * Checks if two edge objects are the same
     * @param obj the second edge object
     * @return returns true if both edges have the same id number
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Edge)) return false;
        if(obj == this) return true;
        
        return this.id == ((Edge)obj).getId();
    }

    /**
     * As this project is using HashSets and HashMaps, in order to check the 
     * equality of two objects, we must implement a hash check on objects. If 
     * multiple objects produces the same hashCode, they are seen as equal.
     * @return the hashCode of the object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        
        return hash;
    }
    
    
}
