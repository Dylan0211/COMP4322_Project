import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.*;
import java.lang.*;
import java.util.*;
public class Graph {
    private Node[] nodes=null;

    public void addNode(Node node){
        if (nodes==null){
            nodes= new Node[]{node};
        }
        else{
            List<Node> tmpList=new ArrayList<Node>(Arrays.asList(nodes));
            tmpList.add(node);
            nodes=tmpList.toArray(nodes);
        }
    }
    public Node[] getNodes(){
        return nodes;
    }
    public Node sourceNode(String sourceName){
        Node source = null;
        for (Node value : nodes) {
            if (value.getName().equals(sourceName)) {
                source = value;
            }
        }
        return source;
    }
    /**
     * This method prints the summary table for compute-all function
     */
    public String getSummaryTable(String sourceName){
        Node source = null;
        StringBuilder sb = new StringBuilder();
        for (Node value : nodes) {
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
}
