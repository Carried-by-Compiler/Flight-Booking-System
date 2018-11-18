public interface Edge {
    public int getId();
    public Node getOrigin();
    public Node getDestination();
    public double getCost();
    public Edge clone();
    public boolean equals(Object obj);
    public int hashCode();
}