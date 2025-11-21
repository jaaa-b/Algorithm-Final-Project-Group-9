import javax.swing.*;
import java.util.List;

public class SimulationWindow {

    public static void start(List<VisualNode> visualNodes) {

        JFrame frame = new JFrame("Drone Path Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);

        GraphPanel panel = new GraphPanel(visualNodes);
        frame.add(panel);

        frame.setVisible(true);
    }
}
