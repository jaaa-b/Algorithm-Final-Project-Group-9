public class VisualNode {

    public int x, y;
    public Node node;

    public VisualNode(Node node, int x, int y) {
        this.node = node;
        this.x = x;
        this.y = y;
    }

    // Check if a mouse click is inside this node
    public boolean contains(int mx, int my) {
        int r = 18;
        int dx = mx - x;
        int dy = my - y;
        return dx * dx + dy * dy <= r * r;
    }
}
