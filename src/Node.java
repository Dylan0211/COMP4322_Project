import java.util.HashMap;
import java.util.LinkedList;

public class Node {
    private String name;
    private LinkedList<Node> shortestPath = new LinkedList<Node>();
    private int distance = Integer.MAX_VALUE;
    HashMap<Node, Integer> adjacentNodes = new HashMap<>();

    public void addAdjacentNode(Node destination, int distance){
        this.adjacentNodes.put(destination, distance);
    }
    public Node(String name){
        this.name = name;
    }

    // getters and setters
    public String getName(){
        return this.name;
    }
    public void setDistance(int distance){
        this.distance = distance;
    }
    public int getDistance(){
        return this.distance;
    }
    public HashMap<Node, Integer> getAdjacentNodes(){
        return this.adjacentNodes;
    }
    public LinkedList<Node> getShortestPath(){
        return this.shortestPath;
    }
    public void setShortestPath(LinkedList<Node> shortestPath){
        this.shortestPath = shortestPath;
    }
}
