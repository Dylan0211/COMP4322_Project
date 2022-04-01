import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import java.util.*;

public class Graph {
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<String> nameList = new ArrayList<String>();

    /**
     * This method adds a new node to the graph
     * @param node node to be added
     */
    public void addNode(Node node) {
        this.nodes.add(node);
        this.nameList.add(node.getName());
    }
    /**
     * This method removes a node from the graph
     * @param node node to be removed
     */
    public void removeNode(Node node){ // need check whether node exists
        this.nodes.remove(node);
        this.nameList.remove(node.getName());
        for (Node eachNode: nodes){
            eachNode.getAdjacentNodes().remove(node);
        }
    }
    /**
     * This method removes a link between two nodes
     * @param startNode first node
     * @param endNode second node
     */
    public void breakLine(Node startNode, Node endNode){ // need check whether nodes exist
        startNode.getAdjacentNodes().remove(endNode);
        endNode.getAdjacentNodes().remove(startNode);
    }
    /**
     * This method gets the node object
     * @param nodeName of a node
     * @return node object / null if not found
     */
    public Node getNode(String nodeName){
        Node target = null;
        for (Node value : this.nodes) {
            if (value.getName().equals(nodeName)) {
                target = value;
            }
        }
        return target;
    }
    /**
     * This method returns the summary table for compute-all function
     * @param source object
     * @return summary table
     */
    public String getSummaryTable(Node source){
        StringBuilder sb = new StringBuilder();
        sb.append("Source ").append(source.getName()).append(": \n");
        Collections.sort(this.nameList);
        for (String nodeName: this.nameList){
            Node node = this.getNode(nodeName);
            if (!nodeName.equals(source.getName())){
                sb.append(nodeName).append(": Path: ");
                for (Node nodeAlongPath: node.getShortestPath()){
                    sb.append(nodeAlongPath.getName()).append(">");
                }
                sb.append(nodeName).append(" ");
                sb.append("Cost: ").append(node.getDistance()).append("\n");
            }
        }
        return sb.toString();
    }
    /**
     * This method outputs the graph as a .lsa file
     * @param filename of file
     */
    public void outputGraph(String filename) {
        StringBuilder sb = new StringBuilder();
        Collections.sort(this.nameList);
        for (String name: this.nameList){
            Node node = this.getNode(name);
            sb.append(name).append(":");
            TreeMap<String, Integer> sortedMap = new TreeMap<>();
            for (Node adjacentNode: node.getAdjacentNodes().keySet())
                sortedMap.put(adjacentNode.getName(), node.getAdjacentNodes().get(adjacentNode));
            for (Map.Entry<String, Integer> entry: sortedMap.entrySet()){
                String nodeName = entry.getKey();
                int edgeWeight = entry.getValue();
                sb.append(" ").append(nodeName).append(":");
                sb.append(edgeWeight);
            }
            sb.append("\n");
        }
        try{
            FileWriter writeToFile = new FileWriter(filename, false);
            writeToFile.write(sb.toString());
            writeToFile.close();
        } catch (IOException e) {
            // some error messages
        }
    }
}
