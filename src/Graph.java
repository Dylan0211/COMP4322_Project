import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import java.util.*;
public class Graph {
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public void addNode(Node node) { this.nodes.add(node); }
    public ArrayList<Node> getNodes(){
        return this.nodes;
    }
    /**
     * This method get the source node object
     * @param sourceName name of source node
     * @return source node object
     */
    public Node getSourceNode(String sourceName){
        Node source = null;
        for (Node value : this.nodes) {
            if (value.getName().equals(sourceName)) {
                source = value;
            }
        }
        return source;
    }
    /**
     * This method returns the summary table for compute-all function
     * @param sourceName name of source node
     * @return summary table
     */
    public String getSummaryTable(String sourceName){
        Node source = null;
        StringBuilder sb = new StringBuilder();
        for (Node value : this.nodes) {
            if (value.getName().equals(sourceName)) {
                source = value;
            }
        }
        if (source!=null){
            sb.append("Source ").append(sourceName).append(": \n");
            for (Node node: this.getNodes()){
                if (!node.getName().equals(source.getName())){
                    sb.append(node.getName()).append(": Path: ");
                    for (Node nodeAlongPath: node.getShortestPath()){
                        sb.append(nodeAlongPath.getName()).append(">");
                    }
                    sb.append(node.getName()).append(" ");
                    sb.append("Cost: ").append(node.getDistance()).append("\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * This method outputs the graph as a .lsa file
     * @param filename name of file
     */
    public void outputGraph(String filename) {
        StringBuilder sb = new StringBuilder();
        for (Node node: this.nodes){
            sb.append(node.getName()).append(":");
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
