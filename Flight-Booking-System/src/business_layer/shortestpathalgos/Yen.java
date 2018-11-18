/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer.shortestpathalgos;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author John Rey Juele
 * 
 * https://github.com/bsmock/k-shortest-shortestPaths
 */
public class Yen implements FlightSearchStrategy {
    
    private ArrayList<Path> shortestPaths;
    private Graph graph;
    
    public Yen() {
        this.shortestPaths = new ArrayList<Path>();
    }
    
    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    
    /**
     * Execute Yen's algorithm which populates the list of shortest paths.
     * 
     * @param source the starting node.
     * @param end the end node.
     */
    @Override
    public boolean execute(Node source, Node end) {
        boolean ok = true;
        
        PriorityQueue<Path> possiblePaths = new PriorityQueue<Path>();
        Graph clonedGraph = this.graph.clone();
        
        // Get A[0] -> the shortest path of the entire graph
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.setGraph(clonedGraph);
        dijkstra.execute(source, end);
        Path shortestPath = dijkstra.getShortestPaths().get(0);
        
        this.shortestPaths.add(shortestPath);
        int K = 20;
        
        try {
            for(int k = 1; k < K; k++) {
                Path previousShortestPath = this.shortestPaths.get(k - 1);

                // Going through each spur node, from the first node to the second
                // to last node in the previous shortest path.
                for(int i = 0; i < previousShortestPath.size(); i++)  {
                    Node spurNode = previousShortestPath.getEdges().get(i).getOrigin();

                    Path rootPath = previousShortestPath.getPathTo(i);

                    for(Path p :  this.shortestPaths) {
                        if(p.getPathTo(i).equals(rootPath)) {
                            // Remove the edge to make way of a diversion
                            Edge edge2Remove = p.getEdges().get(i);
                            clonedGraph.removeEdge(edge2Remove);
                        }
                    }

                    // Remove the nodes that consists of the root path
                    for(Edge rootEdge : rootPath.getEdges()) {
                        Node node2Remove = rootEdge.getOrigin();
                        if(!node2Remove.equals(spurNode)) {
                            clonedGraph.removeNode(node2Remove);
                        }
                    }

                    // Calculate the shortest path from the spur node to the target node

                    /*
                    Possible problems:
                        - What if the second to last node no longer has anymore neighbors
                          when its only edge to the target node is removed? 
                          How will Dijkstra  handle that?
                        - What will we add to the priority queue if the only possible
                          path is the path through the second to last node who's only
                          way to the target node has been removed. If we increment
                          K, there will not be a K-1 in the shortestPaths list.
                    */
                    Path spurPath = null;
                    dijkstra = new Dijkstra();
                    dijkstra.setGraph(clonedGraph);
                    boolean success = dijkstra.execute(spurNode, end);
                    if(success)
                        spurPath = dijkstra.getShortestPaths().get(0);

                    if(spurPath != null) {
                        Path totalPath = rootPath.clone();
                        totalPath.joinPath(spurPath);
                        possiblePaths.add(totalPath);
                    }

                    // Refresh the cloned graph
                    clonedGraph = this.graph.clone();
                }

                boolean addPath = true;
                // get the shortest path from priority queue
                do {
                    addPath = true;
                    shortestPath = possiblePaths.poll();

                    if(shortestPath != null) {
                        // check if possible shortest path already exists preivously.
                        for(Path p : this.shortestPaths) {
                            if(p.equals(shortestPath)) {
                                addPath = false;
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                } while(!addPath);

                // if there are no more possible paths to add, stop the algorithm
                if(shortestPath == null) {
                    break;
                } else {
                    this.shortestPaths.add(shortestPath);
                }
            }  
        } catch(Exception e) {
            System.out.println(e.toString());
            ok = false;
        }
        return ok;
    }
    
    @Override
    public List<Path> getShortestPaths() {
        return this.shortestPaths;
    }
    
    @Override
    public void resetAlgorithm() {
        this.shortestPaths = new ArrayList<Path>();
    }
}
