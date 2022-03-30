import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GUIDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton singleStepButton;
    private JButton computeAllButton;
    private JButton loadfileButton;
    private JTextArea textArea1;
    private JTextField sourceTextField;
    private JTextField selectSourceTextField;
    private JTextArea openedfile;
    Graph graph = new Graph();

    public GUIDialog() {
        contentPane.setPreferredSize(new Dimension(800,600));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        singleStepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSinglestep();
            }
        });
        computeAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onComputeall();
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

    private void onSinglestep(){

    }
    private void onComputeall() {
        graph = Dijkstra.calculateShortestPath(graph, graph.sourceNode(sourceTextField.getText()));
        textArea1.setText(graph.printSummaryTable(sourceTextField.getText()));
    }
    private void onLoadfile() throws IOException {
        String filepath=new File("").getAbsolutePath();
        filepath=filepath+"\\src\\routes.lsa";
        File f = new File(filepath);
        String loadfile="";
        InputStream in = new FileInputStream(f);
        int source_flag=0;
        int node_count=0;
        int size = in.available();

        for (int i = 0; i < size; i++) {
            loadfile=loadfile+(char) in.read();
        }
        in.close();
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

        nodeF.addAdjacentNode(nodeB, 2);
        nodeF.addAdjacentNode(nodeE, 5);
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);
        for (String retval1: loadfile.split("\n")){
            source_flag=0;
            node_count=node_count+1;
            for (String retval2: retval1.split(" ")){
                for(String retval3: retval2.split(":")){
                    if (source_flag==0){
                        Node newNode=new Node(retval3);
                        source_flag=1;
                    }
                }
            }
        }
        System.out.println(node_count);
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
