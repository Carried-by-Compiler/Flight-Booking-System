package business_layer.shortestpathalgos;

/**
 * Represents a Node entity
 * @author John Rey Juele
 */
public class Node {
    private int id;
    private String name;
    
    /**
     * Initialize the node object
     * @param id identifier of the node
     * @param name what the object is called
     */
    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * 
     * @return a string representation of the object 
     */
    @Override
    public String toString() {
        return "Node{" + "id=" + id + ", name=" + name + '}';
    }

    /**
     * 
     * @param obj Another node to compare
     * @return if the ID of parameter node is the same as the id of current object,
     * then it will return true. Otherwise, it will return false.
     */
    @Override
    public boolean equals(Object obj) {
        
        if(obj == null) return false;
        if(!(obj instanceof Node)) return false;
        if(obj == this) return true;
        
        return this.id == ((Node)obj).getId();
    }

    /**
     * As this project is working with HashMaps and HashSets, in order to see if
     * objects are "equal", they would need to posses the same hashCode.
     * @return the object's hashCode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id;
        
        return hash;
    }
    
    public Node clone() {
        return new Node(this.id, this.name);
    }
}
