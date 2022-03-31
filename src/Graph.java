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
        String returnStr="";
        for (Node value : nodes) {
            if (value.getName().equals(sourceName)) {
                source = value;
            }
        }
        if (source!=null){
            returnStr=returnStr+"Source " + sourceName + ": \n";
            for (Node node: this.getNodes()){
                if (!node.getName().equals(source.getName())){
                    returnStr=returnStr+node.getName() + ": Path: ";
                    for (Node nodeAlongPath: node.getShortestPath()){
                        returnStr=returnStr+nodeAlongPath.getName() + ">";
                    }
                    returnStr=returnStr+node.getName() + " ";
                    returnStr=returnStr+"Cost: " + node.getDistance() + "\n";
                }
            }
        }
        return returnStr;
    }
}
