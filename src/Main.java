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

    }
}
