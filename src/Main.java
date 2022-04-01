import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
	    // test dijkstra algorithm
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addAdjacentNode(nodeB, 5);
        nodeA.addAdjacentNode(nodeC, 3);
        nodeA.addAdjacentNode(nodeD, 5);

        nodeB.addAdjacentNode(nodeA, 5);
        nodeB.addAdjacentNode(nodeC, 4);
        nodeB.addAdjacentNode(nodeE, 3);
        nodeB.addAdjacentNode(nodeF, 2);

        nodeC.addAdjacentNode(nodeA, 3);
        nodeC.addAdjacentNode(nodeB, 4);
        nodeC.addAdjacentNode(nodeD, 1);
        nodeC.addAdjacentNode(nodeE, 6);

        nodeD.addAdjacentNode(nodeA, 5);
        nodeD.addAdjacentNode(nodeC, 1);
        nodeD.addAdjacentNode(nodeE, 3);

        nodeE.addAdjacentNode(nodeB, 3);
        nodeE.addAdjacentNode(nodeC, 6);
        nodeE.addAdjacentNode(nodeD, 3);
        nodeE.addAdjacentNode(nodeF, 5);

        nodeF.addAdjacentNode(nodeB, 2);
        nodeF.addAdjacentNode(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeF);
        graph.addNode(nodeA);
        graph.addNode(nodeE);
        graph.addNode(nodeB);
        graph.addNode(nodeD);
        graph.addNode(nodeC);

        // test outputGraph function
        //graph.breakLine(graph.getNode("A"), graph.getNode("C"));
        //graph.outputGraph("test.lsa");

        ArrayList<String> singleSteps = Dijkstra.calculateShortestPath(graph, graph.getNode("A"));
        for (String step: singleSteps){
            System.out.println(step);
        }
        System.out.println("\n"+graph.getSummaryTable(graph.getNode("A")));

    }
}
