package View;

import Model.Dijkstra;
import Model.Graph;
import Model.Node;
import Controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GUIDialog extends JDialog {
    private JPanel panel1;
    private JPanel panel2;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton singleStepButton;
    private JButton computeAllButton;
    private JButton loadfileButton;
    private JButton helpButton;
    private JTextArea textArea1;
    private JScrollPane scroll1;
    private JScrollPane scroll2;
    private JTextField sourceTextField;
    private JTextField newNodeTextField;
    private JTextArea openedfile;
    private JButton addButton;
    private JTextField removeTextField;
    private JButton removeButton;
    private JTextField breakTextfield;
    private JButton breakButton;
    private boolean singlestep_flag=true;
    private String singleStepStr;
    private int singlestep_count=0;
    private ArrayList<String> singleStr;
    Graph graph=new Graph();
    Controller controller = new Controller();

    /**
     * This function is to start GUI and bind functions to each GUI widget
     */
    public GUIDialog() {
        contentPane.setPreferredSize(new Dimension(1000, 600));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onHelp();
            }
        });
        singleStepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onSinglestep();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addNode();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removeNode();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        breakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    breakLink();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        computeAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onComputeall();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        loadfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onLoadfile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * This function is used to remove node from the graph
     * @throws IOException
     */
    private void removeNode() throws IOException {
        controller.removeNode(graph.getNode(removeTextField.getText()),graph);
        controller.outputGraph(graph);
        onLoadfile();
    }

    /**
     * This function is used to break link between nodes
     * @throws IOException
     */
    private void breakLink() throws IOException {
        ArrayList<String> breakNode=new ArrayList<>();
        for (String node: breakTextfield.getText().split(">")){
            breakNode.add(node);
        }
        controller.breakLine(graph.getNode(breakNode.get(0)),graph.getNode(breakNode.get(1)));
        controller.outputGraph(graph);
        onLoadfile();
    }

    /**
     * This function is used to add node to the graph
     * @throws IOException
     */
    private void addNode() throws IOException {
        Node tmpNode = null;
        Node adjunctNode = null;
        int adjunctDistance=0;
        int source_flag=0;
        int duplicate_error=0;
        for (String retval: newNodeTextField.getText().split("[ ]+")) {
            boolean isDistance=false;
            for(String retval2: retval.split(":")){
                if (source_flag==0){
                    tmpNode=new Node(retval2);
                    source_flag=1;
                    for (int i=0;i<graph.getNodesList().size();i++){
                        if (graph.getNodesList().get(i).getName().equals(retval2)){
                            duplicate_error=1;
                        }
                    }
                }
                else {
                    if (!isDistance){
                        isDistance=true;
                        for (int i=0;i<graph.getNodesList().size();i++){
                            if (graph.getNodesList().get(i).getName().equals(retval2)){
                                adjunctNode=graph.getNodesList().get(i);
                                break;
                            }
                        }
                    }
                    else {
                        adjunctDistance=Integer.parseInt(retval2);
                        tmpNode.addAdjacentNode(adjunctNode,adjunctDistance);
                        assert adjunctNode != null;
                        adjunctNode.addAdjacentNode(tmpNode,adjunctDistance);
                    }
                }
            }
        }
        if (duplicate_error==0){
            controller.addNode(tmpNode,graph);
            controller.outputGraph(graph);
            onLoadfile();
            textArea1.setText("Congratulation! The node is added to the graph !");
        }
        else{
            textArea1.setText("Sorry, the node is already in the graph!");
        }
    }

    /**
     * This function does the compute single-step  and prints the results to the GUI
     * @throws IOException
     */
    private void onSinglestep() throws IOException {
        if (sourceTextField.getText().equals("")) {
            textArea1.setText("Error! Please enter a source node!");
        }
        else {
            if (singlestep_flag) {
                graph = new Graph();
                onLoadfile();
                singleStr = controller.getSingleStepOutputs(graph, graph.getNode(sourceTextField.getText()));
                singlestep_flag = false;
                singleStepStr = singleStr.get(singlestep_count);
            } else {
                singleStepStr = singleStepStr + "\n" + singleStr.get(singlestep_count);
            }
            textArea1.setText(singleStepStr);
            singlestep_count = singlestep_count + 1;
            if (singlestep_count == singleStr.size()) {
                singlestep_count = 0;
                singlestep_flag = true;
            }
        }
    }
    /**
     * This function does the compute all-function and prints the results to the GUI
     * @throws IOException
     */
    private void onComputeall() throws IOException {
        if (sourceTextField.getText().equals("")) {
         textArea1.setText("Error! Please enter a source node!");
        }
        else {
            singlestep_flag = true;
            singlestep_count = 0;
            graph = new Graph();
            onLoadfile();
            Dijkstra.calculateShortestPath(graph, graph.getNode(sourceTextField.getText()));
            textArea1.setText(controller.getSummaryTable(graph.getNode(sourceTextField.getText()), graph));
        }
    }

    /**
     * This function loads the lsa file to the program
     * @throws IOException
     */
    private void onLoadfile() throws IOException {
        graph=new Graph();
        ArrayList<Node> tmpNode = new ArrayList<Node>();
        String filepath=new File("").getAbsolutePath();
        filepath=filepath+"\\src\\routes.lsa";
        File f = new File(filepath);
        String loadfile="";
        InputStream in = new FileInputStream(f);
        int source_flag=0;
        int add_flag=0;
        int node_count=0;
        int size = in.available();

        for (int i = 0; i < size; i++) {
            loadfile=loadfile+(char) in.read();
        }
        in.close();
        for (String retval1: loadfile.split("\n")){
            source_flag=0;
            node_count=node_count+1;
            for (String retval2: retval1.split("[ ]+")){
                for(String retval3: retval2.split(":")){
                    if (source_flag==0){
                        Node newNode=new Node(retval3);
                        tmpNode.add(newNode);
                        source_flag=1;
                    }
                }
            }
        }
        for (String retval1: loadfile.split("\n")){
            source_flag=0;
            add_flag=0;
            Node soure_Node = null;
            for (String retval2: retval1.split("[ ]+")){
                Node adjunctNode=null;
                int adjunctDist = 0;
                boolean isDistance=false;
                for(String retval3: retval2.split(":")){
                    if (source_flag==0){
                        source_flag=1;
                        for (int j=0;j<node_count;j++){
                            if (tmpNode.get(j).getName().equals(retval3)){
                                soure_Node=tmpNode.get(j);
                            }
                        }
                    }
                    else {
                        if (!isDistance){
                            for (int j=0;j<node_count;j++){
                                if (tmpNode.get(j).getName().equals(retval3)){
                                    adjunctNode=tmpNode.get(j);
                                }
                            }
                            isDistance=true;
                        }
                        else {
                            if (retval3!=null){
                                try {
                                    retval3=retval3.replace("\n", "").replace("\r", "");
                                    adjunctDist = Integer.parseInt(retval3);
                                }
                                catch (NumberFormatException ex){
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
                if (add_flag==0){
                    add_flag=1;
                }
                else{
                    assert soure_Node != null;
                    assert adjunctNode != null;
                    soure_Node.addAdjacentNode(adjunctNode,adjunctDist);
                }
            }
        }
        for (int i=0;i<node_count;i++) {
            controller.addNode(tmpNode.get(i),graph);
        }
        openedfile.setText(loadfile);
    }

    /**
     * This function is used to dispose the GUI
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * This method creates a new window to show instructive messages
     */
    private void onHelp(){
        String message = "1. How to load file?\n"+
                "* The .lsa file to be loaded by this application is /src/routes.lsa.\n"+
                "* You can click the \"Loadfile\" button to load this .lsa file to this application.\n"+
                "* The content of file will be shown on the right side of the screen.\n"+
                "2. How to get outputs?\n"+
                "* This application has two output modes: single-step mode and compute-all mode.\n"+
                "* After loading the file, click the \"Compute All\" button and the summary table containing\n"+
                "  Shortest path from source node to every node will be generated on the left side of the window.\n"+
                "* After loading the file, click the \"Single Step\" button and the application will display\n"+
                "  a single step through the LSR computation to allow the user to trace the computation path.\n"+
                "  The outputs will be shown on the left side of the window.\n"+
                "3. How to conduct dynamic manipulations over the graph?\n"+
                "* There are in total three kinds of dynamic operations: add new node, remove a node, break a link.\n"+
                "* Sample inputs for each operations:\n"+
                "  Add a new node: H: A:3 C:8 D:2 (please follow the format)\n"+
                "  Remove a node: B (please enter a single node)\n"+
                "  Break a link: A>C (please follow the format)\n"+
                "* The changes will be saved to the routes.lsa file and loaded automatically.\n";
        textArea1.setText(message);
    }

    /**
     * The main function
     * @param args
     */
    public static void main(String[] args) {
        GUIDialog dialog = new GUIDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
