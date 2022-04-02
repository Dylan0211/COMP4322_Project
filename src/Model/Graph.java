package Model;

import java.util.ArrayList;
import java.lang.*;

public class Graph {
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<String> nameList = new ArrayList<String>();

    // getters and setters
    public ArrayList<Node> getNodesList(){
        return this.nodes;
    }
    public ArrayList<String> getNameList(){
        return this.nameList;
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
}
