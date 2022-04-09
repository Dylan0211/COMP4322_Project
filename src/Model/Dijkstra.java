package Model;

import java.util.*;

public class Dijkstra {
    /**
     * This method is used to get the current node under consideration.
     * (which is the node with the smallest distance)
     * @param nodes set of nodes to be considered
     * @return current node
     */
     private static Node getCurrentNode(Set<Node> nodes){
        Node currentNode = null;
        int currentDistance = Integer.MAX_VALUE; // initialize the distance to MAX_VALUE
        for (Node node: nodes){
            int nodeDistance = node.getDistance();
            if (nodeDistance < currentDistance){ // get the node with the smallest distance
                currentDistance = nodeDistance;
                currentNode = node;
            }
        }
        return currentNode;
    }
    /**
     * This method is used to calculate the shortest path from source node to every node
     * in the graph and construct the final graph.
     * @param graph graph to be processed
     * @param source source node
     * @return single-step mode output strings
     */
    public static ArrayList<String> calculateShortestPath(Graph graph, Node source){
        Set<Node> pastNodes = new HashSet<>(); // nodes that are already finalized
        Set<Node> incomingNodes = new HashSet<>(); // nodes that need to be processed
        ArrayList<String> singleStepOutputs = new ArrayList<String>(); // set of all single step outputs

        source.setDistance(0);
        incomingNodes.add(source);

        while(incomingNodes.size() != 0){
            Node currentNode = getCurrentNode(incomingNodes);
            incomingNodes.remove(currentNode);
            for (Node adjacentNode: currentNode.getAdjacentNodes().keySet()){
                StringBuilder sb = new StringBuilder();
                int edgeWeight = currentNode.getAdjacentNodes().get(adjacentNode);
                if (!pastNodes.contains(adjacentNode)){ // do not consider finalized nodes
                    int sourceDistance = currentNode.getDistance();
                    sb.append("Current Node: ").append(currentNode.getName()).append(", ");
                    if (sourceDistance + edgeWeight < adjacentNode.getDistance()){ // distance updates
                        if (adjacentNode.getDistance() == Integer.MAX_VALUE)  // new node found
                            sb.append("Found ").append(adjacentNode.getName()).append(": Path: ");
                        else // distance updates
                            sb.append("Updated ").append(adjacentNode.getName()).append(": Path: ");

                        adjacentNode.setDistance(sourceDistance + edgeWeight);
                        LinkedList<Node> shortestPath = new LinkedList<Node>(currentNode.getShortestPath());
                        shortestPath.add(currentNode);
                        adjacentNode.setShortestPath(shortestPath);

                        for (Node node: adjacentNode.getShortestPath())
                            sb.append(node.getName()).append(">");
                        sb.append(adjacentNode.getName()).append(" ");
                        sb.append("Cost: ").append(adjacentNode.getDistance());
                        sb.append(" [click \"Single Step\" button to continue]");
                        singleStepOutputs.add(sb.toString());
                    }
                    else{ // distance not updates
                        sb.append("Not update ").append(adjacentNode.getName()).append(": Since ");
                        sb.append(sourceDistance).append("+").append(edgeWeight);
                        sb.append(" > ").append(adjacentNode.getDistance()).append(" [click \"Single Step\" button to continue]");
                        singleStepOutputs.add(sb.toString());
                    }
                    incomingNodes.add(adjacentNode);
                }
            }
            pastNodes.add(currentNode);
        }
        singleStepOutputs.add("End of algorithm");
        return singleStepOutputs;
    }
}
