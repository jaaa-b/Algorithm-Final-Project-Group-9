import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class GraphPanel extends JPanel {

    private List<VisualNode> visualNodes;
    private List<Node> currentPath;

    private Node source = null;
    private Node dest = null;

    private int currentIndex = 0;
    private double droneX, droneY;
    private double speed = 3.5;

    public GraphPanel(List<VisualNode> visualNodes) {
        this.visualNodes = visualNodes;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VisualNode clicked = getClickedTown(e.getX(), e.getY());
                if (clicked != null) {
                    handleTownClick(clicked.node);
                }
            }
        });

        new Timer(16, e -> updateDrone()).start();
    }

    private void fullyReset() {
        source = null;
        dest = null;
        currentPath = null;
        currentIndex = 0;

        for (VisualNode vn : visualNodes) {
            vn.node.setDistance(Integer.MAX_VALUE);
            vn.node.setPath(new java.util.LinkedList<>());
        }

        repaint();
    }

    private void handleTownClick(Node clickedNode) {

        // FIRST CLICK — choose SOURCE
        if (source == null) {
            fullyReset();
            source = clickedNode;

            VisualNode vn = findVisualNode(source);
            droneX = vn.x;
            droneY = vn.y;

            repaint();
            System.out.println("SOURCE selected: " + source.getName());
            return;
        }

        // SECOND CLICK — choose DESTINATION
        if (dest == null) {
            dest = clickedNode;
            System.out.println("DEST selected: " + dest.getName());

            // Reset nodes before running Dijkstra
            for (VisualNode vn : visualNodes) {
                vn.node.setDistance(Integer.MAX_VALUE);
                vn.node.setPath(new java.util.LinkedList<>());
            }

            Dijkstra.calculateShortestPath(source, dest);

            currentPath = new java.util.LinkedList<>(dest.getPath());
            currentPath.add(dest);

            VisualNode sn = findVisualNode(source);
            droneX = sn.x;
            droneY = sn.y;
            currentIndex = 0;

            repaint();
            return;
        }

        // THIRD CLICK — FULL RESET + NEW SOURCE
        System.out.println("RESET activated");

        fullyReset();
        source = clickedNode;

        VisualNode start = findVisualNode(source);
        droneX = start.x;
        droneY = start.y;

        repaint();
    }

    private VisualNode getClickedTown(int x, int y) {
        for (VisualNode vn : visualNodes)
            if (vn.contains(x, y)) return vn;
        return null;
    }

    private void updateDrone() {
        if (currentPath == null || currentPath.size() < 2) return;
        if (currentIndex >= currentPath.size() - 1) return;

        VisualNode a = findVisualNode(currentPath.get(currentIndex));
        VisualNode b = findVisualNode(currentPath.get(currentIndex + 1));
        if (a == null || b == null) return;

        double dx = b.x - droneX;
        double dy = b.y - droneY;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) {
            droneX = b.x;
            droneY = b.y;
            currentIndex++;
        } else {
            droneX += (dx / dist) * speed;
            droneY += (dy / dist) * speed;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(240,240,240));

        drawEdges(g);
        drawNodes(g);
        drawDrone(g);
        drawLabels(g);
    }

    private void drawEdges(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        for (VisualNode v1 : visualNodes) {
            for (Map.Entry<Node,Integer> entry : v1.node.getAdjacentNodes().entrySet()) {
                VisualNode v2 = findVisualNode(entry.getKey());
                int w = entry.getValue();

                if (isEdgeOnPath(v1.node, v2.node)) g2.setColor(Color.RED);
                else g2.setColor(Color.GRAY);

                g2.drawLine(v1.x, v1.y, v2.x, v2.y);

                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString("" + w, (v1.x + v2.x)/2, (v1.y + v2.y)/2);
            }
        }
    }

    private void drawNodes(Graphics g) {
        for (VisualNode vn : visualNodes) {
            if (vn.node == source) g.setColor(Color.BLUE);
            else if (vn.node == dest) g.setColor(Color.RED);
            else g.setColor(Color.BLACK);

            g.fillOval(vn.x - 14, vn.y - 14, 28, 28);

            g.setColor(Color.BLACK);
            g.drawString(vn.node.getName(), vn.x - 25, vn.y - 20);
        }
    }

    private void drawDrone(Graphics g) {
        if (currentPath == null) return;

        g.setColor(Color.ORANGE);
        int[] xs = { (int)droneX, (int)droneX - 10, (int)droneX + 10 };
        int[] ys = { (int)droneY - 14, (int)droneY + 10, (int)droneY + 10 };
        g.fillPolygon(xs, ys, 3);
    }

    private void drawLabels(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        g.drawString("Click SOURCE → DEST → RESET", 20, 30);

        if (source != null)
            g.drawString("SOURCE: " + source.getName(), 20, 55);

        if (dest != null)
            g.drawString("DEST: " + dest.getName(), 20, 80);
    }

    private boolean isEdgeOnPath(Node a, Node b) {
        if (currentPath == null) return false;

        for (int i = 0; i < currentPath.size() - 1; i++) {
            if ((currentPath.get(i) == a && currentPath.get(i+1) == b) ||
                    (currentPath.get(i) == b && currentPath.get(i+1) == a))
                return true;
        }
        return false;
    }

    private VisualNode findVisualNode(Node node) {
        for (VisualNode vn : visualNodes)
            if (vn.node == node) return vn;
        return null;
    }
}
