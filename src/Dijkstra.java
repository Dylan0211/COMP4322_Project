import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Dijkstra {
    /**
     * This method is used to get the current node under consideration.
     * (which is the node with the smallest distance)
     * @param nodes set of nodes to be considered
     * @return current node
     */
    public static Node getCurrentNode(Set<Node> nodes){
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
     * This method is used to calculate the minimum distance of a node and
     * update the shortest path correspondingly.
     * If currentNode.getDistance()+edgeWeight < nextNode.getDistance(), the shortest path of
     * nextNode should be changed to the shortest path of currentNode + currentNode.
     * If currentNode.getDistance()+edgeWeight >= nextNode.getDistance(), the shortest path
     * should not change.
     * @param currentNode source node
     * @param nextNode node under consideration
     * @param edgeWeight weight of edge between sourceNode and nextNode
     */
    public static void calculateMinDistance(Node currentNode, Node nextNode, int edgeWeight){
        int sourceDistance = currentNode.getDistance();
        if (sourceDistance + edgeWeight < nextNode.getDistance()){
            nextNode.setDistance(sourceDistance + edgeWeight);
            LinkedList<Node> shortestPath = new LinkedList<Node>(currentNode.getShortestPath());
            shortestPath.add(currentNode);
            nextNode.setShortestPath(shortestPath);
        }
    }
    /**
     * This method is used to calculate the shortest path from source node to every node
     * in the graph and construct the final graph.
     * @param graph graph to be processed
     * @param source source node
     * @return finalized graph
     */
    public static void calculateShortestPath(Graph graph, Node source){
        Set<Node> pastNodes = new HashSet<>(); // nodes that are already finalized
        Set<Node> incomingNodes = new HashSet<>(); // nodes that need to be processed

        source.setDistance(0);
        incomingNodes.add(source);

        while(incomingNodes.size() != 0){
            Node currentNode = getCurrentNode(incomingNodes);
            incomingNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacentEntry: currentNode.getAdjacentNodes().entrySet()){
                Node adjacentNode = adjacentEntry.getKey();
                int edgeWeight = adjacentEntry.getValue();
                if (!pastNodes.contains(adjacentNode)){ // do not consider finalized nodes
                    calculateMinDistance(currentNode, adjacentNode, edgeWeight);
                    incomingNodes.add(adjacentNode);
                }
            }
            pastNodes.add(currentNode);
        }
    }
}
