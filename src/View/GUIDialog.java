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
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton singleStepButton;
    private JButton computeAllButton;
    private JButton loadfileButton;
    private JTextArea textArea1;
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
    public GUIDialog() {
        contentPane.setPreferredSize(new Dimension(1200,600));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

    private void onOK() {
        // add your code here
        dispose();
    }
    private void removeNode() throws IOException {
        controller.removeNode(graph.getNode(removeTextField.getText()),graph);
        controller.outputGraph(graph);
        onLoadfile();
    }
    private void breakLink() throws IOException {
        ArrayList<String> breakNode=new ArrayList<>();
        for (String node: breakTextfield.getText().split(">")){
            breakNode.add(node);
        }
        controller.breakLine(graph.getNode(breakNode.get(0)),graph.getNode(breakNode.get(1)));
        controller.outputGraph(graph);
        onLoadfile();
    }
    private void addNode() throws IOException {
        Node tmpNode = null;
        Node adjunctNode = null;
        int adjunctDistance=0;
        int source_flag=0;
        for (String retval: newNodeTextField.getText().split("[ ]+")) {
            boolean isDistance=false;
            for(String retval2: retval.split(":")){
                if (source_flag==0){
                    tmpNode=new Node(retval2);
                    source_flag=1;
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
        controller.addNode(tmpNode,graph);
        controller.outputGraph(graph);
        onLoadfile();
    }
    private void onSinglestep() throws IOException {
        if (singlestep_flag) {
            graph=new Graph();
            onLoadfile();
            singleStr=controller.getSingleStepOutputs(graph,graph.getNode(sourceTextField.getText()));
            singlestep_flag=false;
            singleStepStr=singleStr.get(singlestep_count);
        }
        else {
            singleStepStr=singleStepStr+"\n"+singleStr.get(singlestep_count);
        }
        textArea1.setText(singleStepStr);
        singlestep_count=singlestep_count+1;
        if (singlestep_count==singleStr.size()){
            singlestep_count=0;
            singlestep_flag=true;
        }
    }
    /**
     * This function do the compute all-function and print the results to the GUI
     * @throws IOException
     */
    private void onComputeall() throws IOException {
        singlestep_flag=true;
        singlestep_count=0;
        graph=new Graph();
        onLoadfile();
        Dijkstra.calculateShortestPath(graph, graph.getNode(sourceTextField.getText()));
        textArea1.setText(controller.getSummaryTable(graph.getNode(sourceTextField.getText()),graph));
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

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        GUIDialog dialog = new GUIDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
