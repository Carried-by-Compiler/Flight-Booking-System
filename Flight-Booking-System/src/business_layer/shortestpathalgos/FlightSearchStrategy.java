/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer.shortestpathalgos;

import java.util.List;

/**
 *
 * @author John Rey Juele
 */
public interface FlightSearchStrategy {
    public void setGraph(Graph graph);
    public boolean execute(Node start, Node end);
    public List<Path> getShortestPaths();
}
