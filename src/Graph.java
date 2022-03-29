import java.util.HashSet;

public class Graph {
    private HashSet<Node> nodes = new HashSet<>();

    public void addNode(Node node){
        nodes.add(node);
    }
    public HashSet<Node> getNodes(){
        return nodes;
    }

    /**
     * This method prints the summary table for compute-all function
     * @param source source node
     */
    public void printSummaryTable(Node source){
        System.out.println("Source " + source.getName() + ":");
        for (Node node: this.getNodes()){
            if (!node.equals(source)){
                System.out.print(node.getName() + ": Path: ");
                for (Node nodeAlongPath: node.getShortestPath()){
                    System.out.print(nodeAlongPath.getName() + ">");
                }
                System.out.print(node.getName() + " ");
                System.out.print("Cost: " + node.getDistance() + "\n");
            }
        }
    }

    /**
     * This method prints the output for single step function
     * @param newNode new node founded
     */
    public void printSingleStepOutput(Node newNode){
        System.out.print("Found " + newNode.getName() + ": Path: ");
        for (Node nodeAlongPath: newNode.getShortestPath()){
            System.out.print(nodeAlongPath.getName() + ">");
        }
        System.out.print(newNode.getName() + " ");
        System.out.print("Cost: " + newNode.getDistance() + "\n");
    }
}
