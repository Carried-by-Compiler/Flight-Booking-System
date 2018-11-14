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
public class Yen {
    
    private ArrayList<Path> shortestPaths;
    private List<Node> nodes;
    private List<Edge> edges;
    private Graph graph;
    
    public Yen(Graph graph) {
        this.nodes = graph.getNodes();
        this.edges = graph.getEdges();
        this.graph = graph;
        
        this.shortestPaths = new ArrayList<Path>();
    }
    
    /**
     * Execute Yen's algorithm which populates the list of shortest paths.
     * 
     * @param source the starting node.
     * @param target the end node.
     * @param K the number of shortest paths.
     */
    public ArrayList<Path> execute(Node source, Node target, int K) {
        boolean addPath = true;
        boolean canProgress = false;
        PriorityQueue<Path> B = new PriorityQueue<Path>();
        /*
        Use the clone as dummy graph. Renew the clone with the graph data member of this class
        */
        Graph graphClone = this.graph.clone();
        // Get and add the most shortest path as the first path
        Dijkstra dijkstra = new Dijkstra(new Graph(nodes, edges), target);
        dijkstra.execute(source);
        Path path = dijkstra.getShortestPath();
        
        shortestPaths.add(path);
        
        // Get the next K - 1 shortest paths
        for(int k = 1; k < K; k++) {
            if(k - this.shortestPaths.size() >= 1) {
                break;
            }
            Path previousPath = shortestPaths.get(k - 1);
            
            for(int i = 0; i < previousPath.size(); i++) {
                Node spurNode = previousPath.getEdges().get(i).getOrigin();
                
                Path rootPath = previousPath.getPathTo(i);
                
                for(Path p : shortestPaths) {
                    Path stub = p.getPathTo(i);
                    
                    // Remove next edge in previous shortest path
                    if(rootPath.equals(stub)) {
                        Edge edge2Remove = p.getEdges().get(i);
                        graphClone.removeEdge(edge2Remove.getOrigin(), edge2Remove.getDestination());
                        //canProgress = graphClone.checkIfPathOutExists()
                    }
                }
                
                // Remove all nodes in root path except the spurNode from graph clone
                for(Edge rootEdge : rootPath.getEdges()) {
                    Node node2Remove = rootEdge.getOrigin();
                    if(!node2Remove.equals(spurNode)) {
                        graphClone.removeNode(node2Remove);
                    }
                }
                // Get shortest path from spur node to target node in reduced graph
                Path spurPath = null;
                dijkstra = new Dijkstra(graphClone, target);
                boolean success = dijkstra.execute(spurNode);
                if(success)
                    spurPath = dijkstra.getShortestPath();
                
                if(spurPath != null) {
                    Path totalPath = rootPath.clone();
                    totalPath.joinPath(spurPath);
                    B.add(totalPath);
                }
                // Refresh the graph clone to original state
                graphClone = this.graph.clone();
            }
            
            // Get shortest path from B
            path = B.poll();
            
            if(path != null) {
                // Check if this path already exists in the list of shortest paths
                
                for(Path p : shortestPaths) {
                    addPath = true;
                    if(p.equals(path)) {
                        addPath = false;
                    }
                }
                
                if(addPath) {
                    shortestPaths.add(path);
                    k--;
                }
            }
            
        }
        return this.shortestPaths;
    }
}
