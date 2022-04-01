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

    private void onSinglestep(){

    }

    /**
     * This function do the compute all-function and print the results to the GUI
     * @throws IOException
     */
    private void onComputeall() throws IOException {
        graph=new Graph();
        onLoadfile();
        Dijkstra.calculateShortestPath(graph, graph.getSourceNode(sourceTextField.getText()));
        textArea1.setText(graph.getSummaryTable(graph.getSourceNode(sourceTextField.getText())));
    }

    /**
     * This function loads the lsa file to the program
     * @throws IOException
     */
    private void onLoadfile() throws IOException {
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
            for (String retval2: retval1.split(" ")){
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
            for (String retval2: retval1.split(" ")){
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
            graph.addNode(tmpNode.get(i));
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
