/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer.shortestpathalgos;

/**
 *
 * @author John Rey Juele
 */
public interface Edge<T> {
    public int getId();
    public Node getOrigin();
    public Node getDestination();
    public double getCost();
    public T clone();
    public boolean equals(Object obj);
    public int hashCode();
}
