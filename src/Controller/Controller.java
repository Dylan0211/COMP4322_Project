package Controller;

import Model.Graph;
import Model.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Controller {
    /**
     * This method adds a new node to the graph
     * @param node node to be added
     */
    public void addNode(Node node, Graph graph) {
        graph.getNodesList().add(node);
        graph.getNameList().add(node.getName());
    }
    /**
     * This method removes a node from the graph
     * @param node node to be removed
     */
    public void removeNode(Node node, Graph graph){ // need check whether node exists
        graph.getNodesList().remove(node);
        graph.getNameList().remove(node.getName());
        for (Node eachNode: graph.getNodesList()){
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
     * This method returns the summary table for compute-all function
     * @param source object
     * @return summary table
     */
    public String getSummaryTable(Node source, Graph graph){
        StringBuilder sb = new StringBuilder();
        sb.append("Source ").append(source.getName()).append(": \n");
        Collections.sort(graph.getNameList());
        for (String nodeName: graph.getNameList()){
            Node node = graph.getNode(nodeName);
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
     * @param graph to be output
     */
    public void outputGraph(Graph graph) {
        StringBuilder sb = new StringBuilder();
        Collections.sort(graph.getNameList());
        for (String name: graph.getNameList()){
            Node node = graph.getNode(name);
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
        String filepath = new File("").getAbsolutePath();
        try{
            FileWriter writeToFile = new FileWriter(filepath+"\\src\\routes.lsa", false);
            writeToFile.write(sb.toString());
            writeToFile.close();
        } catch (IOException e) {
            // some error messages
        }
    }
}
