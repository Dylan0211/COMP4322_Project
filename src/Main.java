import Model.Dijkstra;
import Model.Graph;
import Model.Node;
import Controller.Controller;
import View.GUIDialog;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        // test GUI
        GUIDialog dialog = new GUIDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);

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

        controller.addNode(nodeA, graph);
        controller.addNode(nodeB, graph);
        controller.addNode(nodeC, graph);
        controller.addNode(nodeD, graph);
        controller.addNode(nodeE, graph);
        controller.addNode(nodeF, graph);

        // test outputGraph function
        controller.removeNode(graph.getNode("B"), graph);
        controller.outputGraph(graph);

        ArrayList<String> singleSteps = Dijkstra.calculateShortestPath(graph, graph.getNode("A"));
        for (String step: singleSteps){
            System.out.println(step);
        }
        System.out.println("\n"+controller.getSummaryTable(graph.getNode("A"), graph));

    }
}
